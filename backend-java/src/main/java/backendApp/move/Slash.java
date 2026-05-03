package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;

import java.util.Arrays;
import java.util.List;

public class Slash extends AbstractMove {
    public Slash() {
        super("slash", "Slash", "damage",
                "A swift sword strike. Physical damage scaling off Attack.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("attack", 80));
    }
}
