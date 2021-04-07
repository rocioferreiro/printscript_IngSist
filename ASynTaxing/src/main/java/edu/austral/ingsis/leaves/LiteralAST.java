package edu.austral.ingsis.leaves;

import edu.austral.ingsis.ContextBuilder;
import edu.austral.ingsis.Token;
import edu.austral.ingsis.VariableBuilder;

public class LiteralAST implements ASTLeaf {

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
    if (context.toAddExists()) return context.setToAddValue(token.getValue());
    return context.addVariable(new VariableBuilder().setValue(token.getValue()));
  }
}
