package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.BuffSelfEffect;

import java.util.Arrays;
import java.util.List;

public class Frenzy extends AbstractMove {
    public Frenzy() {
        super("frenzy", "Frenzy", "buff",
                "Works into a rage, raising own Attack by 35% for 2 turns.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new BuffSelfEffect("attack", 35, 2));
    }
}
