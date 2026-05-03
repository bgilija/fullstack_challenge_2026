package backendApp.move;
import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.DamageEffect;
import backendApp.effect.DebuffTargetEffect;

import java.util.Arrays;
import java.util.List;

public class WebThrow extends AbstractMove {
    public WebThrow() {
        super("web_throw", "Web Throw", "damage",
                "Light physical damage and lowers target Defense by 20% for 2 turns.", MoveType.PHYSICAL);
    }
    @Override
    protected List<AbstractMoveEffect> buildEffects() {
        return Arrays.asList(new DamageEffect("attack", 40), new DebuffTargetEffect("defense", 20, 2));
    }
}
