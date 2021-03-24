package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public enum Operator implements TokenType {
    EQUAL("=", "EQUAL", "EQUAL"),
    T_ASSIGNATION(":", "TYPE ASSIGNATION", "DECLARE_TYPE"),
    PLUS("\\+", "OPERATOR", "OPERATION"),
    SUBTRACTION("-", "OPERATOR", "OPERATION"),
    MULTIPLICATION("\\*", "OPERATOR", "OPERATION"),
    DIVISION("\\/", "OPERATOR", "OPERATION");

    private final String regex;
    private final String sub;
    private final String category;

    Operator(String regex, String sub, String category) {
        this.regex = regex;
        this.sub = sub;
        this.category = category;
    }

    @Override
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

    public static List<Token> findTokens(String string, Position initialPosition){
        List<Token> finalList = new ArrayList<>();
        String acc = "";
        for (int i = 0; i < string.length(); i++) {
            String ch = Character.toString(string.charAt(i));
            boolean match = false;
            for(Operator key : values()){
                if(ch.matches(key.getRegex())){
                    if(!acc.isEmpty()) finalList.add(new ProvisionalToken(acc, initialPosition.incrementColumn((i))));
                    finalList.add(new ConcreteToken(key, ch, initialPosition.incrementColumn(i+1)));
                    acc = "";
                    match = true;
                }
            }
            if(!match) acc += ch;
        }
        if(!acc.isEmpty() && finalList.size()>0) finalList.add(new ProvisionalToken(acc, finalList.get(finalList.size()-1).getPosition().incrementColumn(acc.length())));
        return finalList.isEmpty() ? List.of(new ProvisionalToken(string, initialPosition)) : finalList;
    }
}
