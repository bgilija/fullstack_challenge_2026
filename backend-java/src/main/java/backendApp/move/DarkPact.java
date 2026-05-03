package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.BuffSelfEffect;
import backendApp.effect.HpCostEffect;

import java.util.Arrays;
import java.util.List;

public class DarkPact extends AbstractMove {
    public DarkPact() {
        super("dark_pact", "Dark Pact", "buff",
                "Raises own Magic by 40% for 2 turns, costs 15% max HP.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        // two effects: buff magic AND pay HP
        return Arrays.asList(new BuffSelfEffect("magic", 40, 2), new HpCostEffect(15));
    }
}
