package backendApp.monster;
import backendApp.transferObjects.StatBlock;


import backendApp.move.*;

public class GoblinWarrior extends AbstractMonster {

    public GoblinWarrior() {
        super("goblin_warrior", "Goblin Warrior",
                "A small but feisty brawler. Aggressive and unpredictable.",
                new StatBlock(80, 20, 8, 5), 60);
    }

    @Override
    protected void buildMoves() {
        addMove(new RustyBlade());
        addMove(new DirtyKick());
        addMove(new Frenzy());
        addMove(new Headbutt());
    }
}
