package backendApp.monster;
import backendApp.transferObjects.StatBlock;


import backendApp.move.*;

public class GoblinMage extends AbstractMonster {

    public GoblinMage() {
        super("goblin_mage", "Goblin Mage",
                "A goblin who has mastered the arcane arts. Dangerous at range.",
                new StatBlock(110, 15, 12, 40), 150);
    }

    @Override
    protected void buildMoves() {
        addMove(new Firebolt());
        addMove(new ArcaneSurge());
        addMove(new ManaDrain());
        addMove(new HexShield());
    }
}
