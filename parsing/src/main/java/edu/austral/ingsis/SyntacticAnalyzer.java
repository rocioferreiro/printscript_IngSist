package edu.austral.ingsis;

import java.util.List;

public interface SyntacticAnalyzer {
  ASTWrapper analyze(List<Token> tokens);
}
