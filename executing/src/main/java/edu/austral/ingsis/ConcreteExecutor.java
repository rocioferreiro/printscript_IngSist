package edu.austral.ingsis;

public class ConcreteExecutor implements Executor {

  public ConcreteExecutor() {}

  @Override
  public void execute(AST ast, Context context) {
    context = ast.executeTree(new ContextBuilder(context)).build();
    context.execute();
  }
}
