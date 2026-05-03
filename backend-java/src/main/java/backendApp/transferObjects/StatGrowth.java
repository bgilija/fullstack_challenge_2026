package backendApp.transferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatGrowth {
    private int health;
    private int attack;
    private int defense;
    private int magic;
}
