package edu.austral.ingsis;

import java.util.List;

public class ConcreteParser implements Parser {

    private SyntacticAnalyzer syntacticAnalyzer;
    private SemanticAnalyzer semanticAnalyzer;

    @Override
    public AST parse(List<Token> tokens) {
        return null;
    }
}
