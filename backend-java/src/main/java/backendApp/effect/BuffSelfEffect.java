package backendApp.effect;

import backendApp.transferObjects.BattleBuff;
import backendApp.transferObjects.MoveEffect;

// Temporarily raises one of caster's stats.
public class BuffSelfEffect extends AbstractMoveEffect {

    private final String stat;
    private final int percent;
    private final int duration;

    public BuffSelfEffect(String stat, int percent, int duration) {
        this.stat = stat;
        this.percent = percent;
        this.duration = duration;
    }

    @Override public String getKind() { return "buff_self"; }

    @Override
    public MoveEffect toDto() {
        return new MoveEffect("buff_self", null, null, stat, percent, duration);
    }

    @Override
    public void apply(CombatContext ctx) {
        ctx.casterBuffsAdded.add(new BattleBuff(stat, percent, duration));
        ctx.logParts.add("+" + percent + "% " + stat + " for " + duration + " turns");
    }
}
