package edu.austral.ingsis;

public class LiteralAST implements ASTLeaf {

  private Token token;

  // TODO

  @Override
  public Token getToken() {
    return token;
  }

  @Override
  public AST addAST(AST ast) {
    return null;
  }
}
