package backendApp.effect;

import backendApp.transferObjects.MoveEffect;


public class DamageEffect extends AbstractMoveEffect {

    private final String scaling;
    private final int baseValue;

    public DamageEffect(String scaling, int baseValue) {
        this.scaling = scaling;
        this.baseValue = baseValue;
    }

    @Override public String getKind() { return "damage"; }

    @Override
    public MoveEffect toDto() {
        return new MoveEffect("damage", scaling, baseValue, null, null, null);
    }

    @Override
    public void apply(CombatContext ctx) {
        int dmg;
        if ("attack".equals(scaling)) {
            int effAtk = ctx.effectiveCasterStat("attack");
            int effDef = ctx.effectiveTargetStat("defense");
            dmg = Math.max(1, (int) Math.floor(effAtk * baseValue / 100.0 - effDef * 0.3));
        } else {
            int effMag = ctx.effectiveCasterStat("magic");
            dmg = Math.max(1, (int) Math.floor(effMag * baseValue / 100.0));
        }
        ctx.targetHpChange -= dmg;
        ctx.logParts.add(dmg + " damage");
    }
}
