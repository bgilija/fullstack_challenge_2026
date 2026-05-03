package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.HealSelfEffect;

import java.util.Arrays;
import java.util.List;

public class SecondWind extends AbstractMove {
    public SecondWind() {
        super("second_wind", "Second Wind", "heal",
                "Heals self for a moderate amount. Scales off Magic.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new HealSelfEffect(80));
    }
}
