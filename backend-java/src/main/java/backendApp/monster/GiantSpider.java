package backendApp.monster;
import backendApp.transferObjects.StatBlock;


import backendApp.move.*;

public class GiantSpider extends AbstractMonster {

    public GiantSpider() {
        super("giant_spider", "Giant Spider",
                "A massive arachnid. Uses webs to cripple its prey before striking.",
                new StatBlock(100, 25, 12, 8), 90);
    }

    @Override
    protected void buildMoves() {
        addMove(new Bite());
        addMove(new WebThrow());
        addMove(new Pounce());
        addMove(new Skitter());
    }
}
