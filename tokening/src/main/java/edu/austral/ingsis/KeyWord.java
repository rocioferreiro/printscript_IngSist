package edu.austral.ingsis;

import java.util.Locale;

public enum KeyWord implements TokenType {
    DECLARATION("let", "DECLARATION"),
    S_ASSIGNATION("string", "TYPE"),
    STRING("[\"'].*[\"']", "VALUE"),
    N_ASSIGNATION("number", "TYPE"),
    NUMBER("\\d+(\\.\\d+)?", "VALUE"),
    PRINTLN("println", "PRINTLN"),
    VARIABLE_REF("[a-zA-Z0-9]+([_a-zA-Z0-9]*)", "VARIABLE");

    private final String regex;
    private final String category;

    KeyWord(String regex, String category) {
        this.regex = regex;
        this.category = category;
    }
    public String getRegex() {
        return regex;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public int getOrdinal() {
        return ordinal();
    }

    public static Token findToken(Token token){
        for(KeyWord key : values()){
            if(token.getValue().matches(key.getRegex())){
                return new ConcreteToken(key, token.getValue(), token.getPosition());
            }
        }
        return token;
    }
}
