package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.BuffSelfEffect;

import java.util.Arrays;
import java.util.List;

public class HexShield extends AbstractMove {
    public HexShield() {
        super("hex_shield", "Hex Shield", "buff",
                "A magical barrier raising own Defense by 35% for 2 turns.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new BuffSelfEffect("defense", 35, 2));
    }
}
