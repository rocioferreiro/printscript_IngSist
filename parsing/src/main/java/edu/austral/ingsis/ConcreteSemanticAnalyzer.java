package edu.austral.ingsis;

import edu.austral.ingsis.rules.RuleType;

public class ConcreteSemanticAnalyzer implements SemanticAnalyzer {

  private final Context context;

  public ConcreteSemanticAnalyzer() {
    context = new Context();
  }

  @Override
  public void analyze(ASTWrapper ast) {
    updateContext(ast);
  }

  public Context getContext() {
    return context;
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
