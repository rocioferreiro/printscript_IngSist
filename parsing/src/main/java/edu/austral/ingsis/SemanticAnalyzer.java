package edu.austral.ingsis;

public interface SemanticAnalyzer {

  void analyze(ASTWrapper ast);

  Context getContext();
}
