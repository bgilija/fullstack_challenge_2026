package backendApp.transferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroConfig {
    private String name;
    private StatBlock baseStats;
    private StatGrowth statGrowth;
    private List<String> defaultMoves;
    private List<Integer> xpThresholds;
}
