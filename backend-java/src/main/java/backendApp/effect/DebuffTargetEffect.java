package backendApp.effect;

import backendApp.transferObjects.BattleBuff;
import backendApp.transferObjects.MoveEffect;

// lowers one of the targets stats.
public class DebuffTargetEffect extends AbstractMoveEffect {

    private final String stat;
    private final int percent;
    private final int duration;

    public DebuffTargetEffect(String stat, int percent, int duration) {
        this.stat = stat;
        this.percent = percent;
        this.duration = duration;
    }

    @Override public String getKind() { return "debuff_target"; }

    @Override
    public MoveEffect toDto() {
        return new MoveEffect("debuff_target", null, null, stat, percent, duration);
    }

    @Override
    public void apply(CombatContext ctx) {
        ctx.targetBuffsAdded.add(new BattleBuff(stat, -percent, duration));
        ctx.logParts.add("-" + percent + "% target " + stat + " for " + duration + " turns");
    }
}
