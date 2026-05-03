package backendApp.transferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigResponse {
    private List<Monster> monsters;
    private Map<String, Move> moves;
    private HeroConfig hero;
}
