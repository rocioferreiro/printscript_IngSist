package edu.austral.ingsis;

public interface ExecutionStrategy {

  void execute(Executor executor, ASTInContext astInContext);
}
