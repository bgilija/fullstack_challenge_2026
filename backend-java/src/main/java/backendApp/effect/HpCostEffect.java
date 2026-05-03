package backendApp.effect;

import backendApp.transferObjects.MoveEffect;

// The caster pays a percentage of their max hp to use the move
public class HpCostEffect extends AbstractMoveEffect {

    private final int percent;

    public HpCostEffect(int percent) { this.percent = percent; }

    @Override public String getKind() { return "hp_cost"; }

    @Override
    public MoveEffect toDto() {
        return new MoveEffect("hp_cost", null, null, null, percent, null);
    }

    @Override
    public void apply(CombatContext ctx) {
        int cost = (int) Math.floor(ctx.casterMaxHp * percent / 100.0);
        ctx.casterHpChange -= cost;
        ctx.logParts.add("costs " + cost + " HP");
    }
}
