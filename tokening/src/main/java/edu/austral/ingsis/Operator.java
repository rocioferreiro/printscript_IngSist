package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public enum Operator implements TokenType{
    EQUAL("=", "EQUAL"),
    T_ASSIGNATION(":", "TYPE ASSIGNATION"),
    PLUS("\\+", "PLUS SIGN"),
    SUBTRACTION("-", "SUBTRACTION SIGN"),
    MULTIPLICATION("\\*", "MULTIPLICATION SIGN"),
    DIVISION("\\/", "DIVISION TYPE");

    private final String regex;
    private final String id;

    Operator(String regex, String id) {
        this.regex = regex;
        this.id = id;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public String identifier() {
        return id;
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
