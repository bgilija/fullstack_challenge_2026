package backendApp.service;

import backendApp.transferObjects.BattleBuff;
import backendApp.transferObjects.Monster;
import backendApp.transferObjects.Move;
import backendApp.transferObjects.MoveEffect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MonsterAiService {

    @Autowired
    private GameDataService gameDataService;

    private final Random random = new Random();

    public String pickMove(String monsterId, double monsterHp, double monsterMaxHp,
                           double heroHp, double heroMaxHp,
                           List<BattleBuff> monsterBuffs, int turn) {

        Monster monster = gameDataService.getMonsterById(monsterId);

        if (monster == null) {
            System.out.println("ERROR: could not find monster with id: " + monsterId);
            return null;
        }

        List<String> availableMoves = monster.getMoves();
        double monsterHpPercent = monsterHp / monsterMaxHp;
        double heroHpPercent = heroHp / heroMaxHp;

        // if monster is low hp, play defensive option
        if (monsterHpPercent < 0.3) {
            for (String moveId : availableMoves) {
                Move move = gameDataService.getMoveById(moveId);
                if (move == null) continue;
                if (move.getCategory().equals("buff") && !moveId.equals("dark_pact")) {
                    if (!isBuffAlreadyActive(monsterBuffs, move)) {
                        return moveId;
                    }
                }
            }
        }

        // if hero is low hp, go for the kill
        if (heroHpPercent < 0.35) {
            String strongestDamageMove = findStrongestDamageMove(availableMoves);
            if (strongestDamageMove != null) {
                return strongestDamageMove;
            }
        }

        // debuff in first couple of turns

        if (turn <= 2) {
            for (String moveId : availableMoves) {
                Move move = gameDataService.getMoveById(moveId);
                if (move != null && move.getCategory().equals("debuff")) {
                    return moveId;
                }
            }
        }

        //random move
        return availableMoves.get(random.nextInt(availableMoves.size()));
    }

    private String findStrongestDamageMove(List<String> moveIds) {
        String bestMove = null;
        int bestValue = 0;

        for (String moveId : moveIds) {
            Move move = gameDataService.getMoveById(moveId);
            if (move == null || !move.getCategory().equals("damage")) continue;

            for (MoveEffect effect : move.getEffects()) {
                if (effect.getBaseValue() != null && effect.getBaseValue() > bestValue) {
                    bestValue = effect.getBaseValue();
                    bestMove = moveId;
                }
            }
        }

        return bestMove;
    }

    private boolean isBuffAlreadyActive(List<BattleBuff> monsterBuffs, Move move) {
        for (MoveEffect effect : move.getEffects()) {
            if ("buff_self".equals(effect.getKind()) && effect.getStat() != null) {
                for (BattleBuff buff : monsterBuffs) {
                    if (buff.getStat().equals(effect.getStat()) && buff.getPercent() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
