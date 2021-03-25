package edu.austral.ingsis;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ConcreteRule implements Rule{

    private final RuleType type;
    private final String acceptingRegex;

    public ConcreteRule(RuleType type, String acceptingRegex) {
        this.type = type;
        this.acceptingRegex = acceptingRegex;
    }

    public RuleType getType() {
        return type;
    }

    public String getAcceptingRegex() {
        return acceptingRegex;
    }

    @Override
    public boolean validateTokens(List<Token> list) {
        String concat = list.stream().map(t -> t.getType().getCategory()).collect(Collectors.joining(","));
        return concat.matches(acceptingRegex);
    }

    @Override
    public RuleType getRuleType() {
        return null;
    }

}
