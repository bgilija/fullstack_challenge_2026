package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;
import backendApp.effect.DebuffTargetEffect;

import java.util.Arrays;
import java.util.List;

public class ManaDrain extends AbstractMove {
    public ManaDrain() {
        super("mana_drain", "Mana Drain", "damage",
                "Magic damage and lowers target Magic by 20% for 2 turns.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("magic", 45), new DebuffTargetEffect("magic", 20, 2));
    }
}
