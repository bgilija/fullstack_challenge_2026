package backendApp.transferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO for a monster -  to the frontend as part of the config response.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Monster {
    private String id;
    private String name;
    private String description;
    private StatBlock stats;
    private List<String> moves;
    private int xpReward;
}
