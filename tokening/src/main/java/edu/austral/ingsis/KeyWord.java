package edu.austral.ingsis;

public enum KeyWord implements TokenType{
    DECLARATION("let", "DECLARATION"),
    STRING("[\"'].*[\"']", "STRING"),
    S_ASSIGNATION("string", "S_ASSIGNATION"),
    N_ASSIGNATION("number", "N_ASSIGNATION"),
    NUMBER("\\d+", "NUMBER"),
    VARIABLE_REF("[a-zA-Z0-9]+", "REF TO VARIABLE");

    private final String regex;
    private final String id;

    KeyWord(String regex, String id) {
        this.regex = regex;
        this.id = id;
    }

    public String getRegex() {
        return regex;
    }

    @Override
    public String identifier() {
        return id;
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
