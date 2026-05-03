package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DebuffTargetEffect;

import java.util.Arrays;
import java.util.List;

public class Intimidate extends AbstractMove {
    public Intimidate() {
        super("intimidate", "Intimidate", "debuff",
                "A terrifying roar lowers target's Attack by 25% for 2 turns.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DebuffTargetEffect("attack", 25, 2));
    }
}
