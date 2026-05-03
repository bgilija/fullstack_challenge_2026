package backendApp.move;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MoveType {
    PHYSICAL("physical"),
    MAGIC("magic");

    private final String value;

    MoveType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
