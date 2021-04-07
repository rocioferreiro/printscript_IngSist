package edu.austral.ingsis.leaves;

import edu.austral.ingsis.ContextBuilder;
import edu.austral.ingsis.Token;
import edu.austral.ingsis.VariableType;

public class StringAssignationAST implements ASTLeaf {

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
    return context.setToAddType(new VariableType(token.getValue(), token.getType().getOrdinal()));
  }
}
