package edu.austral.ingsis;

public class ProvisionalToken implements Token{

    private final String word;
    private final Position position;

    public ProvisionalToken(String word, Position position) {
        this.word = word;
        this.position = position;
    }

    @Override
    public TokenType getType() {
        return TokenType.invalid;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getValue() {
        return word;
    }
}
