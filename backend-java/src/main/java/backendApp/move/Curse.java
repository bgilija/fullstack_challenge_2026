package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DebuffTargetEffect;

import java.util.Arrays;
import java.util.List;

public class Curse extends AbstractMove {
    public Curse() {
        super("curse", "Curse", "debuff",
                "Lowers target's Attack by 25% for 2 turns.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DebuffTargetEffect("attack", 25, 2));
    }
}
