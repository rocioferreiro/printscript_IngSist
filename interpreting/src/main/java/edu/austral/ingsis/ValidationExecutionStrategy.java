package edu.austral.ingsis;

public class ValidationExecutionStrategy implements ExecutionStrategy {

  @Override
  public void execute(Executor executor, ASTInContext astInContext) {
    showContext(astInContext.getContext());
  }

  private void showContext(Context context) {
    for (Variable v : context.getVariables()) {
      System.out.println(v.toString());
    }
  }
}
