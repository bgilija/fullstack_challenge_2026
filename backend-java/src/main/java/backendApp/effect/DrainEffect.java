package backendApp.effect;

import backendApp.transferObjects.MoveEffect;

// Deals magic damage and heals caster for the same amount.
public class DrainEffect extends AbstractMoveEffect {

    private final int baseValue;

    public DrainEffect(int baseValue) { this.baseValue = baseValue; }

    @Override public String getKind() { return "drain"; }

    @Override
    public MoveEffect toDto() {
        return new MoveEffect("drain", "magic", baseValue, null, null, null);
    }

    @Override
    public void apply(CombatContext ctx) {
        int effMag = ctx.effectiveCasterStat("magic");
        int dmg = Math.max(1, (int) Math.floor(effMag * baseValue / 100.0));
        ctx.targetHpChange -= dmg;
        ctx.casterHpChange += dmg;
        ctx.logParts.add("drained " + dmg + " HP");
    }
}
