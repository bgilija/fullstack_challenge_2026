package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;

import java.util.Arrays;
import java.util.List;

public class Firebolt extends AbstractMove {
    public Firebolt() {
        super("firebolt", "Firebolt", "damage",
                "A bolt of fire dealing moderate magic damage. Scales off Magic.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("magic", 85));
    }
}
