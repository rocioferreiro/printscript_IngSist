package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConcreteSyntacticAnalyzer implements SyntacticAnalyzer {

    private final List<Rule> rules;

    public ConcreteSyntacticAnalyzer(Path rulePath) {
        List<Rule> rulesToAdd = new ArrayList<>();
        String[] ruleTexts = PathReader.read(rulePath).split("\n").clone();
        for (String rule : ruleTexts){
            String[] array = rule.split(":");
            RuleType type = RuleType.ruleOfId(array[0].charAt(0));
            rulesToAdd.add(new ConcreteRule(type, array[1]));
        }
        rules = rulesToAdd;
    }

    @Override
    public Sentence analyze(List<Token> tokens) {
        RuleType type = checkRules(tokens);
        return new Sentence(tokens, type);
    }

    private RuleType checkRules(List<Token> tokens) {
        for(Rule rule: rules){
            if(rule.validateTokens(tokens)) return rule.getRuleType();
        }
        throw new RuntimeException("Invalid Expresion: " + tokens.toString());
    }
}
