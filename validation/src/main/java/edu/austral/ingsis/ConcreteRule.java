package edu.austral.ingsis;

import java.util.List;
import java.util.stream.Collectors;

public class ConcreteRule implements Rule{

    private final RuleType type;
    private final String acceptingRegex;

    public ConcreteRule(RuleType type, String acceptingRegex) {
        this.type = type;
        this.acceptingRegex = acceptingRegex;
    }

    @Override
    public boolean validateTokens(List<Token> list) {
        String concat = list.stream().map(t -> t.getType().getCategory()).collect(Collectors.joining(","));
        return concat.matches(acceptingRegex);
    }

    @Override
    public RuleType getRuleType() {
        return type;
    }

    @Override
    public String getAcceptingRegex() {
        return acceptingRegex;
    }

}
