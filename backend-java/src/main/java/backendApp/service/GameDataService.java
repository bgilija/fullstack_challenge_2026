package backendApp.service;

import backendApp.transferObjects.*;
import backendApp.monster.*;
import backendApp.move.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GameDataService {

    private final HashMap<String, AbstractMove> domainMoves = new HashMap<>();
    private final HashMap<String, Move> moveDtos = new HashMap<>();
    private final List<Monster> monsters = new ArrayList<>();
    private HeroConfig heroConfig;

    @PostConstruct
    public void init() {
        loadMonsters();
        loadHeroMoves();
        loadHero();
        System.out.println("Game data loaded! Moves: " + domainMoves.size() + ", Monsters: " + monsters.size());
    }

    private void loadMonsters() {
        List<AbstractMonster> allMonsters = Arrays.asList(
                new GoblinWarrior(),
                new GiantSpider(),
                new Witch(),
                new GoblinMage(),
                new Dragon()
        );
        for (AbstractMonster monster : allMonsters) {
            monsters.add(monster.toDto());
            monster.getMoveMap().forEach((id, domainMove) -> {
                domainMoves.put(id, domainMove);
                moveDtos.put(id, domainMove.toDto());
            });
        }
    }

    private void loadHeroMoves() {
        List<AbstractMove> heroMoves = Arrays.asList(
                new Slash(), new ShieldUp(), new BattleCry(), new SecondWind()
        );
        for (AbstractMove move : heroMoves) {
            domainMoves.put(move.getId(), move);
            moveDtos.put(move.getId(), move.toDto());
        }
    }

    private void loadHero() {
        heroConfig = new HeroConfig(
                "Knight",
                new StatBlock(120, 25, 15, 20),
                new StatGrowth(20, 5, 4, 5),
                Arrays.asList("slash", "shield_up", "battle_cry", "second_wind"),
                Arrays.asList(100, 250, 450, 700)
        );
    }


    public Map<String, Move> getAllMoves()  { return moveDtos; }
    public Move getMoveById(String id)      { return moveDtos.get(id); }

    public AbstractMove getMoveForCombat(String id) { return domainMoves.get(id); }

    public List<Monster> getAllMonsters()   { return monsters; }
    public HeroConfig getHeroConfig()       { return heroConfig; }

    public Monster getMonsterById(String id) {
        for (Monster monster : monsters) {
            if (monster.getId().equals(id)) return monster;
        }
        return null;
    }
}