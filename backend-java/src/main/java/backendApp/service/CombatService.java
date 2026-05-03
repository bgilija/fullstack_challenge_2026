package backendApp.service;

import backendApp.effect.CombatContext;
import backendApp.move.AbstractMove;
import backendApp.transferObjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CombatService {

    @Autowired
    private GameDataService gameDataService;

    @Autowired
    private MonsterAiService monsterAiService;

    private final Random random = new Random();

    public BattleTurnResponse processTurn(BattleTurnRequest request) {
        AbstractMove heroMove = gameDataService.getMoveForCombat(request.getHeroMoveId());
        Monster monster = gameDataService.getMonsterById(request.getMonsterId());
        StatBlock ms = monster.getStats();


        CombatContext heroCtx = resolveMove(
                heroMove,
                request.getHeroMaxHp(), request.getHeroAttack(), request.getHeroDefense(), request.getHeroMagic(),
                request.getHeroBuffs(),
                ms.getHealth(), ms.getAttack(), ms.getDefense(), ms.getMagic(),
                request.getMonsterBuffs()
        );

        int monsterHpAfter = clamp(request.getMonsterHp() + heroCtx.targetHpChange, ms.getHealth());
        int heroHpAfterHeroMove = clamp(request.getHeroHp() + heroCtx.casterHpChange, request.getHeroMaxHp());

        List<BattleBuff> heroBuffsAfterHeroMove = merge(request.getHeroBuffs(), heroCtx.casterBuffsAdded);
        List<BattleBuff> monsterBuffsAfterHeroMove = merge(request.getMonsterBuffs(), heroCtx.targetBuffsAdded);

        String heroLog = "Knight uses " + heroMove.getName() + "! "
                + String.join(", ", heroCtx.logParts) + ".";


        if (monsterHpAfter <= 0) {
            HeroConfig heroConfig = gameDataService.getHeroConfig();
            int xpGained = monster.getXpReward();
            int newXp = request.getHeroXp() + xpGained;
            int newLevel = computeLevel(newXp, heroConfig.getXpThresholds());
            boolean leveledUp = newLevel > request.getHeroLevel();
            StatBlock newStats = computeStats(heroConfig, newLevel);

            List<String> heroLearned = request.getHeroLearnedMoves() != null ? request.getHeroLearnedMoves() : List.of();
            List<String> learnable = monster.getMoves().stream()
                    .filter(id -> !heroLearned.contains(id))
                    .collect(Collectors.toList());
            String learnedMoveId = learnable.isEmpty() ? null : learnable.get(random.nextInt(learnable.size()));

            boolean runComplete = request.getMonsterIndex() >= request.getTotalMonsters() - 1;

            return new BattleTurnResponse(
                    heroHpAfterHeroMove, 0,
                    heroBuffsAfterHeroMove, monsterBuffsAfterHeroMove,
                    heroLog, null, "hero",
                    xpGained, newXp, newLevel, leveledUp,
                    newStats.getHealth(), newStats.getAttack(), newStats.getDefense(), newStats.getMagic(),
                    learnedMoveId, runComplete, 0
            );
        }

        String monsterMoveId = monsterAiService.pickMove(
                monster.getId(),
                monsterHpAfter, ms.getHealth(),
                heroHpAfterHeroMove, request.getHeroMaxHp(),
                monsterBuffsAfterHeroMove, request.getTurn()
        );

        AbstractMove monsterMove = gameDataService.getMoveForCombat(monsterMoveId);

        CombatContext monsterCtx = resolveMove(
                monsterMove,
                ms.getHealth(), ms.getAttack(), ms.getDefense(), ms.getMagic(),
                monsterBuffsAfterHeroMove,
                request.getHeroMaxHp(), request.getHeroAttack(), request.getHeroDefense(), request.getHeroMagic(),
                heroBuffsAfterHeroMove
        );

        int heroHpAfter = clamp(heroHpAfterHeroMove + monsterCtx.targetHpChange, request.getHeroMaxHp());
        int monsterHpFinal = clamp(monsterHpAfter + monsterCtx.casterHpChange, ms.getHealth());

        List<BattleBuff> heroBuffsAfter    = tickBuffs(merge(heroBuffsAfterHeroMove, monsterCtx.targetBuffsAdded));
        List<BattleBuff> monsterBuffsAfter = tickBuffs(merge(monsterBuffsAfterHeroMove, monsterCtx.casterBuffsAdded));

        String monsterLog = monster.getName() + " uses " + monsterMove.getName() + "! "
                + String.join(", ", monsterCtx.logParts) + ".";

        String winner = heroHpAfter <= 0 ? "monster" : null;

        return new BattleTurnResponse(
                heroHpAfter, monsterHpFinal,
                heroBuffsAfter, monsterBuffsAfter,
                heroLog, monsterLog, winner,
                0, 0, 0, false, 0, 0, 0, 0,
                null, false, request.getTurn() + 1
        );
    }


    private CombatContext resolveMove(AbstractMove move,
                                      int casterMaxHp, int casterAtk, int casterDef, int casterMag,
                                      List<BattleBuff> casterBuffs,
                                      int targetMaxHp, int targetAtk, int targetDef, int targetMag,
                                      List<BattleBuff> targetBuffs) {
        CombatContext ctx = new CombatContext(
                casterMaxHp, casterAtk, casterDef, casterMag, casterBuffs,
                targetMaxHp, targetAtk, targetDef, targetMag, targetBuffs
        );
        move.applyEffects(ctx);
        return ctx;
    }

    private List<BattleBuff> merge(List<BattleBuff> existing, List<BattleBuff> added) {
        List<BattleBuff> merged = new ArrayList<>(existing);
        merged.addAll(added);
        return merged;
    }

    private List<BattleBuff> tickBuffs(List<BattleBuff> buffs) {
        return buffs.stream()
                .map(b -> new BattleBuff(b.getStat(), b.getPercent(), b.getTurnsLeft() - 1))
                .filter(b -> b.getTurnsLeft() > 0)
                .collect(Collectors.toList());
    }

    private int clamp(int value, int max) {
        return Math.min(max, Math.max(0, value));
    }

    private int computeLevel(int xp, List<Integer> thresholds) {
        int level = 1;
        for (int threshold : thresholds) {
            if (xp >= threshold) level++;
            else break;
        }
        return Math.min(level, thresholds.size() + 1);
    }

    private StatBlock computeStats(HeroConfig config, int level) {
        StatBlock base = config.getBaseStats();
        StatGrowth growth = config.getStatGrowth();
        int lvl = level - 1;
        return new StatBlock(
                base.getHealth()  + lvl * growth.getHealth(),
                base.getAttack()  + lvl * growth.getAttack(),
                base.getDefense() + lvl * growth.getDefense(),
                base.getMagic()   + lvl * growth.getMagic()
        );
    }
}