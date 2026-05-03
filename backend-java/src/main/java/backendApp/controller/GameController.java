package backendApp.controller;

import backendApp.transferObjects.*;
import backendApp.service.CombatService;
import backendApp.service.GameDataService;
import backendApp.service.MonsterAiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameDataService gameDataService;

    @Autowired
    private MonsterAiService monsterAiService;

    @Autowired
    private CombatService combatService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/config")
    public ConfigResponse getConfig() {
        ConfigResponse response = new ConfigResponse();
        response.setMonsters(gameDataService.getAllMonsters());
        response.setMoves(gameDataService.getAllMoves());
        response.setHero(gameDataService.getHeroConfig());
        return response;
    }


    @PostMapping("/battle/turn")
    public BattleTurnResponse processTurn(@RequestBody BattleTurnRequest request) {
        return combatService.processTurn(request);
    }

    @GetMapping("/monster-move")
    public ResponseEntity<Map<String, String>> getMonsterMove(
            @RequestParam String monsterId,
            @RequestParam double monsterHp,
            @RequestParam double monsterMaxHp,
            @RequestParam double heroHp,
            @RequestParam double heroMaxHp,
            @RequestParam(required = false, defaultValue = "[]") String monsterBuffs,
            @RequestParam(required = false, defaultValue = "[]") String heroBuffs,
            @RequestParam(defaultValue = "1") int turn) {

        if (gameDataService.getMonsterById(monsterId) == null) {
            return ResponseEntity.notFound().build();
        }

        List<BattleBuff> parsedMonsterBuffs = parseBuffsFromJson(monsterBuffs);
        List<BattleBuff> parsedHeroBuffs    = parseBuffsFromJson(heroBuffs);

        String moveId = monsterAiService.pickMove(
                monsterId, monsterHp, monsterMaxHp,
                heroHp, heroMaxHp,
                parsedMonsterBuffs, turn
        );

        Map<String, String> result = new HashMap<>();
        result.put("moveId", moveId);
        return ResponseEntity.ok(result);
    }

    private List<BattleBuff> parseBuffsFromJson(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<BattleBuff>>() {});
        } catch (Exception e) {
            System.out.println("Warning: could not parse buffs JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
