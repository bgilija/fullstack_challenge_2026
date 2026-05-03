package backendApp.transferObjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoveEffect {
    private String kind;
    private String scaling;
    private Integer baseValue;
    private String stat;
    private Integer percent;
    private Integer duration;
}
