package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.BuffSelfEffect;

import java.util.Arrays;
import java.util.List;

public class BattleCry extends AbstractMove {
    public BattleCry() {
        super("battle_cry", "Battle Cry", "buff",
                "Raises own Attack by 30% for 2 turns.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new BuffSelfEffect("attack", 30, 2));
    }
}
