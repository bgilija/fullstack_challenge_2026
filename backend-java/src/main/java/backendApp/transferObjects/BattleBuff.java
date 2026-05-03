package backendApp.transferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleBuff {
    private String stat;
    private int percent;   // positive = buff, negative = debuff
    private int turnsLeft;
}
