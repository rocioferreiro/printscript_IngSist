package edu.austral.ingsis;

public interface Token {

    TokenType getType();
    Position getPosition();
    String getValue();
}
