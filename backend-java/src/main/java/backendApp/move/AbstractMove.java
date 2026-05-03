package backendApp.move;

import backendApp.effect.AbstractMoveEffect;
import backendApp.effect.CombatContext;
import backendApp.transferObjects.Move;
import backendApp.transferObjects.MoveEffect;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractMove {

    protected String id;
    protected String name;
    protected String category;
    protected String description;
    protected MoveType type;

    public AbstractMove(String id, String name, String category, String description, MoveType type) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.type = type;
    }

    // each subclass returns typed domain effect objects
    protected abstract List<AbstractMoveEffect> buildEffects();

    // converts domain effects to DTOs and wraps in a MoveDto for the frontend
    public Move toDto() {
        List<MoveEffect> effectDtos = new ArrayList<>();
        for (AbstractMoveEffect effect : buildEffects()) {
            effectDtos.add(effect.toDto());
        }
        return new Move(id, name, category, description, effectDtos, type);
    }

    // Executes all effects of this move against the given context.
    // CombatService calls this instead of iterating effects manually.
    public void applyEffects(CombatContext ctx) {
        for (AbstractMoveEffect effect : buildEffects()) {
            effect.apply(ctx);
        }
    }

    public String getId()          { return id; }
    public String getName()        { return name; }
    public String getCategory()    { return category; }
    public String getDescription() { return description; }
}
