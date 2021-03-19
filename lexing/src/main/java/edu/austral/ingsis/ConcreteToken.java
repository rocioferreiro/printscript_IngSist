package edu.austral.ingsis;

public class ConcreteToken implements Token{

    private final KeyWord type;
    private final String value;
    private final Position position;

    public ConcreteToken(KeyWord type, Position position) {
        this.type = type;
        this.value = type.getValue();
        this.position = position;
    }

    public ConcreteToken(KeyWord type, String value, Position position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    public KeyWord getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Position getPosition() {
        return position;
    }
}
