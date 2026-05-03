package backendApp.effect;

import backendApp.transferObjects.MoveEffect;

// Restores health to the caster. Scales off Magic.
public class HealSelfEffect extends AbstractMoveEffect {

    private final int baseValue;

    public HealSelfEffect(int baseValue) { this.baseValue = baseValue; }

    @Override public String getKind() { return "heal_self"; }

    @Override
    public MoveEffect toDto() {
        return new MoveEffect("heal_self", "magic", baseValue, null, null, null);
    }

    @Override
    public void apply(CombatContext ctx) {
        int effMag = ctx.effectiveCasterStat("magic");
        int heal = Math.max(1, (int) Math.floor(effMag * baseValue / 100.0));
        ctx.casterHpChange += heal;
        ctx.logParts.add("healed " + heal + " HP");
    }
}
