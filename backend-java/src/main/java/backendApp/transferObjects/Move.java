package backendApp.transferObjects;

import backendApp.move.MoveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {
    private String id;
    private String name;
    private String category;
    private String description;
    private List<MoveEffect> effects;
    private MoveType type;
}
