package backendApp.transferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleTurnRequest {
    private String heroMoveId;
    private String monsterId;

    private int heroHp;
    private int heroMaxHp;
    private int heroAttack;
    private int heroDefense;
    private int heroMagic;
    private List<BattleBuff> heroBuffs;


    private int monsterHp;
    private List<BattleBuff> monsterBuffs;

    private int turn;
    private int heroXp;
    private int heroLevel;

    private List<String> heroLearnedMoves;
    private int monsterIndex;
    private int totalMonsters;
}
