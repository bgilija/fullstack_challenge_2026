package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;

import java.util.Arrays;
import java.util.List;

public class FlameBreath extends AbstractMove {
    public FlameBreath() {
        super("flame_breath", "Flame Breath", "damage",
                "Scorching flames deal heavy magic damage. Scales off Magic.", MoveType.MAGIC);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("magic", 130));
    }
}
