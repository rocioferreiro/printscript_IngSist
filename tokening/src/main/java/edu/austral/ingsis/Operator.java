package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public enum Operator implements TokenType {
    EQUAL("=", "EQUAL"),
    T_ASSIGNATION(":", "DECLARE_TYPE"),
    PLUS("\\+", "OPERATOR"),
    SUBTRACTION("-", "OPERATOR"),
    MULTIPLICATION("\\*", "OPERATOR"),
    DIVISION("\\/", "OPERATOR"),
    SEMICOLONS(";", "SEPARATOR");

    private final String regex;
    private final String category;

    Operator(String regex, String category) {
        this.regex = regex;
        this.category = category;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public static List<Token> findTokens(String string, Position initialPosition){
        List<Token> finalList = new ArrayList<>();
        String acc = "";
        AtomicInteger column = new AtomicInteger();
        for (Character c : string.toCharArray()) {
            String ch = Character.toString(c);
            boolean match = false;
            for(Operator key : values()){
                if(ch.matches(key.getRegex())){
                    if(!acc.isEmpty()) finalList.add(new ProvisionalToken(acc, initialPosition.incrementColumn(column.getAndAdd(acc.length()))));
                    finalList.add(new ConcreteToken(key, ch, initialPosition.incrementColumn(column.getAndIncrement())));
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
