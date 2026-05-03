package backendApp.effect;

import backendApp.transferObjects.BattleBuff;

import java.util.ArrayList;
import java.util.List;


public class CombatContext {

    public final int casterMaxHp;
    public final int casterAtk;
    public final int casterDef;
    public final int casterMag;
    public final List<BattleBuff> casterBuffs;

    public final int targetMaxHp;
    public final int targetAtk;
    public final int targetDef;
    public final int targetMag;
    public final List<BattleBuff> targetBuffs;

    public int casterHpChange = 0;
    public int targetHpChange = 0;
    public final List<BattleBuff> casterBuffsAdded = new ArrayList<>();
    public final List<BattleBuff> targetBuffsAdded = new ArrayList<>();
    public final List<String> logParts = new ArrayList<>();

    public CombatContext(int casterMaxHp, int casterAtk, int casterDef, int casterMag,
                         List<BattleBuff> casterBuffs,
                         int targetMaxHp, int targetAtk, int targetDef, int targetMag,
                         List<BattleBuff> targetBuffs) {
        this.casterMaxHp = casterMaxHp;
        this.casterAtk = casterAtk;
        this.casterDef = casterDef;
        this.casterMag = casterMag;
        this.casterBuffs = casterBuffs;
        this.targetMaxHp = targetMaxHp;
        this.targetAtk = targetAtk;
        this.targetDef = targetDef;
        this.targetMag = targetMag;
        this.targetBuffs = targetBuffs;
    }

    public int effectiveCasterStat(String stat) {
        return effectiveStat(casterBaseValue(stat), casterBuffs, stat);
    }

    public int effectiveTargetStat(String stat) {
        return effectiveStat(targetBaseValue(stat), targetBuffs, stat);
    }

    private int casterBaseValue(String stat) {
        return switch (stat) {
            case "attack"  -> casterAtk;
            case "defense" -> casterDef;
            case "magic"   -> casterMag;
            default        -> 1;
        };
    }

    private int targetBaseValue(String stat) {
        return switch (stat) {
            case "attack"  -> targetAtk;
            case "defense" -> targetDef;
            case "magic"   -> targetMag;
            default        -> 1;
        };
    }

    private int effectiveStat(int base, List<BattleBuff> buffs, String statName) {
        int totalPct = buffs.stream()
                .filter(b -> b.getStat().equals(statName))
                .mapToInt(BattleBuff::getPercent)
                .sum();
        return Math.max(1, (int) Math.floor(base * (1 + totalPct / 100.0)));
    }
}
