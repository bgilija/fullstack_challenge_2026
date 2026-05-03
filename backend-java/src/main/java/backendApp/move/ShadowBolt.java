package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;

import java.util.Arrays;
import java.util.List;

public class ShadowBolt extends AbstractMove {
    public ShadowBolt() {
        super("shadow_bolt", "Shadow Bolt", "damage",
                "Heavy magic damage bypassing Defense. Scales off Magic.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("magic", 120));
    }
}
