package edu.austral.ingsis.leaves;

import edu.austral.ingsis.Token;

public class NumberAssignationAST implements ASTLeaf {

  private Token token;

  @Override
  public Token getToken() {
    return token;
  }

  @Override
  public void setToken(Token token) {
    this.token = token;
  }
}
