package backendApp.monster;

import backendApp.transferObjects.Monster;
import backendApp.transferObjects.StatBlock;
import backendApp.move.AbstractMove;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public abstract class AbstractMonster {

    protected String id;
    protected String name;
    protected String description;
    protected StatBlock stats;
    protected int xpReward;

    protected LinkedHashMap<String, AbstractMove> moveMap = new LinkedHashMap<>();

    public AbstractMonster(String id, String name,
                           String description, StatBlock stats, int xpReward) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stats = stats;
        this.xpReward = xpReward;
        buildMoves();
    }
    protected abstract void buildMoves();

    public Monster toDto() {
        return new Monster(
                id, name, description, stats,
                new ArrayList<>(moveMap.keySet()),
                xpReward
        );
    }

    public Map<String, AbstractMove> getMoveMap() {
        return moveMap;
    }

    protected void addMove(AbstractMove move) {
        moveMap.put(move.getId(), move);
    }
}
