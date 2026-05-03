package backendApp.effect;

import backendApp.transferObjects.MoveEffect;

public abstract class AbstractMoveEffect {

    public abstract String getKind();

    public abstract MoveEffect toDto();

    public abstract void apply(CombatContext ctx);
}
