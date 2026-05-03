package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;

import java.util.Arrays;
import java.util.List;

public class Pounce extends AbstractMove {
    public Pounce() {
        super("pounce", "Pounce", "damage",
                "Leaps at the enemy for heavy physical damage. Scales off Attack.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("attack", 120));
    }
}
