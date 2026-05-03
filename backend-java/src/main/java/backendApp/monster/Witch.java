package backendApp.monster;
import backendApp.transferObjects.StatBlock;


import backendApp.move.*;

public class Witch extends AbstractMonster {

    public Witch() {
        super("witch", "Witch",
                "A cunning sorceress who channels dark magic. Bypasses armor with ease.",
                new StatBlock(90, 15, 10, 35), 120);
    }

    @Override
    protected void buildMoves() {
        addMove(new ShadowBolt());
        addMove(new DrainLife());
        addMove(new Curse());
        addMove(new DarkPact());
    }
}
