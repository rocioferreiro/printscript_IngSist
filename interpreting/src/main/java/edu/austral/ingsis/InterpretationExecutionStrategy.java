package edu.austral.ingsis;

public class InterpretationExecutionStrategy implements ExecutionStrategy {


  @Override
  public void execute(Executor executor, ASTInContext astInContext) {
    executor.execute(astInContext.getAst(), astInContext.getContext());
  }
}
