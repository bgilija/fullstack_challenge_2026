package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;

import java.util.Arrays;
import java.util.List;

public class Bite extends AbstractMove {
    public Bite() {
        super("bite", "Bite", "damage",
                "A vicious bite. Physical damage scaling off Attack.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("attack", 80));
    }
}
