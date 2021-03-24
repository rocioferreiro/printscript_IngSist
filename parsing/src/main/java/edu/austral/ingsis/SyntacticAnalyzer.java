package edu.austral.ingsis;

import java.util.List;

public interface SyntacticAnalyzer {
    AST analyze(List<Token> tokens);
}
