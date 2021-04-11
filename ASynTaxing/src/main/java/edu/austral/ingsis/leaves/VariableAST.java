package edu.austral.ingsis.leaves;

import edu.austral.ingsis.ContextBuilder;
import edu.austral.ingsis.Token;
import edu.austral.ingsis.Variable;
import edu.austral.ingsis.VariableBuilder;

public class VariableAST implements ASTLeaf {

  private Token token;

  @Override
  public Token getToken() {
    return token;
  }

  @Override
  public void setToken(Token token) {
    this.token = token;
  }

  @Override
  public ContextBuilder executeTree(ContextBuilder context) {
    Variable var = context.getVariable(token.getValue());
    if (context.toAddExists()) return context.setToAddValue(var.getValue()).setToAddIsConst(var.isConst());
    return context.addVariable(new VariableBuilder().withName(token.getValue()).withValue(var.getValue())).setToAddIsConst(var.isConst());
  }
}
