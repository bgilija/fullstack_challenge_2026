package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;

import java.util.Arrays;
import java.util.List;

public class Headbutt extends AbstractMove {
    public Headbutt() {
        super("headbutt", "Headbutt", "damage",
                "A reckless headbutt dealing heavy physical damage. Scales off Attack.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("attack", 115));
    }
}
