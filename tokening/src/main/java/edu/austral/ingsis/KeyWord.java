package edu.austral.ingsis;

public enum KeyWord implements TokenType {
    DECLARATION("let", "DECLARATION"),
    STRING("[\"'].*[\"']", "VALUE"),
    S_ASSIGNATION("string", "TYPE"),
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

    public static Token findToken(Token token){
        for(KeyWord key : values()){
            if(token.getValue().matches(key.getRegex())){
                return new ConcreteToken(key, token.getValue(), token.getPosition());
            }
        }
        return token;
    }
}
