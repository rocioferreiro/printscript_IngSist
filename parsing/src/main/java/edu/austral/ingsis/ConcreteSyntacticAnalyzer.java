package edu.austral.ingsis;

import java.util.List;

public class ConcreteSyntacticAnalyzer implements SyntacticAnalyzer {

    private List<Rule> rules;

    @Override
    public AST analyze(List<Token> tokens) {
        checkRules(tokens);
        return null;
    }

    private void checkRules(List<Token> tokens) {

    }
}
