package edu.austral.ingsis;

import java.util.List;

public interface SyntacticAnalyzer {
    Sentence analyze(List<Token> tokens);
}
