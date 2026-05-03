package backendApp.monster;
import backendApp.transferObjects.StatBlock;


import backendApp.move.*;

public class Dragon extends AbstractMonster {

    public Dragon() {
        super("dragon", "Dragon",
                "The ultimate challenge. Ancient, powerful, and utterly merciless.",
                new StatBlock(200, 45, 30, 50), 200);
    }

    @Override
    protected void buildMoves() {
        addMove(new FlameBreath());
        addMove(new ClawSwipe());
        addMove(new Intimidate());
        addMove(new DragonScales());
    }
}
