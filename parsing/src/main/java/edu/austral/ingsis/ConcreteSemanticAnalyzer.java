package edu.austral.ingsis;

import edu.austral.ingsis.rules.RuleType;

public class ConcreteSemanticAnalyzer implements SemanticAnalyzer {

  private Context context;

  public ConcreteSemanticAnalyzer() {
    context = new Context();
    context = context.setContexts();
  }

  @Override
  public void analyze(ASTWrapper ast) {
    if (ast.getType().equals(RuleType.IF)) updateIfContext(ast);
    else updateContext(ast);
  }

  public Context getContext() {
    return context;
  }

  private void updateIfContext(ASTWrapper wrapper) {
    Context actual = new Context(context);
    for (ASTWrapper ast : wrapper.getLeft()) {
      updateContext(ast);
    }
    Context ifContext = getContext();
    context = actual; // TODO puede causar problemas
    for (ASTWrapper ast : wrapper.getRight()) {
      updateContext(ast);
    }
    Context elseContext = getContext();
    context = new Context(context.getVariables(), ifContext, elseContext);
  }

  private void updateContext(ASTWrapper sentence) {
    Variable variable = sentence.getType().getCommand().execute(sentence.getTree(), context);
    variable.setIsConst(sentence.getType().equals(RuleType.CONST));
    if (!variable.getName().isEmpty()) {
      if (context.checkVariable(variable)) {
        if (!context.checkType(variable))
          throw new InvalidCodeException(
              "Type mismatch!", sentence.getTree().getToken().getPosition());
      } else {
        context.addVariable(variable);
      }
    }
  }
}
