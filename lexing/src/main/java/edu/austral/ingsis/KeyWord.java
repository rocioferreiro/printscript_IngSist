package edu.austral.ingsis;

import java.util.Optional;

public enum KeyWord {
    EQUAL("="),
    DECLARATION("let"),
    T_ASSIGNATION(":"),
    PLUS("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    VARIABLE_REF,
    VARIABLE_VAL;

    private String value = "";

    KeyWord(String s) {
        value = s;
    }

    KeyWord() {
    }

    public String getValue() {
        return value;
    }

    public static Optional<Token> findToken(String string, Position position){
        for(KeyWord key : values()){
            if(key.getValue().equals(string)){
                return Optional.of(new ConcreteToken(key, position));
            }
        }
        return Optional.empty();
    }
}
