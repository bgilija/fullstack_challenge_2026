package backendApp.transferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleTurnResponse {
    private int heroHpAfter;
    private int monsterHpAfter;
    private List<BattleBuff> heroBuffsAfter;
    private List<BattleBuff> monsterBuffsAfter;
    private String heroLog;
    private String monsterLog;
    private String winner;

    // when winner=="hero"
    private int xpGained;
    private int newXp;
    private int newLevel;
    private boolean leveledUp;
    private int newMaxHp;
    private int newAttack;
    private int newDefense;
    private int newMagic;

    private String learnedMoveId;
    private boolean runComplete;
    private int nextTurnNumber;
}
