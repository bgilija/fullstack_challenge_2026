package backendApp.move;

import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.BuffSelfEffect;

import java.util.Arrays;
import java.util.List;

public class ArcaneSurge extends AbstractMove {
    public ArcaneSurge() {
        super("arcane_surge", "Arcane Surge", "buff",
                "Channels arcane energy, raising own Magic by 35% for 2 turns.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new BuffSelfEffect("magic", 35, 2));
    }
}
