package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.BuffSelfEffect;

import java.util.Arrays;
import java.util.List;

public class DragonScales extends AbstractMove {
    public DragonScales() {
        super("dragon_scales", "Dragon Scales", "buff",
                "Hardens scales, raising own Defense by 40% for 2 turns.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new BuffSelfEffect("defense", 40, 2));
    }
}
