package edu.austral.ingsis;

public class ConcreteToken implements Token{

    private final TokenType type;
    private final String value;
    private final Position position;

//    public ConcreteToken(KeyWord type, Position position) {
//        this.type = type;
//        this.value = type.getValue();
//        this.position = position;
//    }

    public ConcreteToken(TokenType type, String value, Position position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String toString(){
        return "Type: " + type.identifier() + ", Value: " + value + ", Position: (" + position.getRow() + ", " + position.getColumn() + ")";
    }
}
