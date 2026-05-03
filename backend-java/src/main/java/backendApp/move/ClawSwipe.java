package backendApp.move;

import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;

import java.util.Arrays;
import java.util.List;

public class ClawSwipe extends AbstractMove {
    public ClawSwipe() {
        super("claw_swipe", "Claw Swipe", "damage",
                "A powerful physical swipe. Scales off Attack.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("attack", 90));
    }
}
