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
            RuleType type = RuleType.ruleOfId(Integer.getInteger(array[0]));
            rulesToAdd.add(new ConcreteRule(type, array[1]));
        }
        rules = rulesToAdd;
    }

    @Override
    public AST analyze(List<Token> tokens) {
        checkRules(tokens);
        return createTree(tokens);
    }

    private AST createTree(List<Token> tokens) {
        return null;
    }

    private void checkRules(List<Token> tokens) {
        for(Rule rule: rules){
            if(rule.validateTokens(tokens)) return;
        }
        throw new RuntimeException("Invalid Expresion: " + tokens.toString());
    }
}
