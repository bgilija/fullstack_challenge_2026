package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DrainEffect;

import java.util.Arrays;
import java.util.List;

public class DrainLife extends AbstractMove {
    public DrainLife() {
        super("drain_life", "Drain Life", "damage",
                "Magic damage that heals the caster for the same amount.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DrainEffect(50));
    }
}
