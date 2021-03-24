package edu.austral.ingsis;

public enum KeyWord implements TokenType {
    DECLARATION("let", "DECLARATION", "DECLARATION"),
    STRING("[\"'].*[\"']", "VALUE", "OPERATION"),
    S_ASSIGNATION("string", "S_ASSIGNATION", "TYPE"),
    N_ASSIGNATION("number", "N_ASSIGNATION", "TYPE"),
    NUMBER("\\d+", "VALUE", "OPERATION"),
    VARIABLE_REF("[a-zA-Z0-9]+", "REF TO VARIABLE", "VARIABLE");

    private final String regex;
    private final String sub;
    private final String category;

    KeyWord(String regex, String sub, String category) {
        this.regex = regex;
        this.sub = sub;
        this.category = category;
    }
    public String getRegex() {
        return regex;
    }

    @Override
    public String getSub() {
        return sub;
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
