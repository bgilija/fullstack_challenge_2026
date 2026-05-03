package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;

import java.util.Arrays;
import java.util.List;

public class RustyBlade extends AbstractMove {
    public RustyBlade() {
        super("rusty_blade", "Rusty Blade", "damage",
                "A rusty but sharp strike. Physical damage scaling off Attack.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("attack", 75));
    }
}
