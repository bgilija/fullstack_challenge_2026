package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.BuffSelfEffect;

import java.util.Arrays;
import java.util.List;

public class Skitter extends AbstractMove {
    public Skitter() {
        super("skitter", "Skitter", "buff",
                "Raises own Defense by 30% for 2 turns.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new BuffSelfEffect("defense", 30, 2));
    }
}
