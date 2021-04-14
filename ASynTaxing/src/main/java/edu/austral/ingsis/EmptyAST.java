package edu.austral.ingsis;

public class EmptyAST implements AST {

  public EmptyAST(Token token) {
  }

  public EmptyAST() {
  }

  @Override
  public Token getToken() {
    return null;
  }

  @Override
  public AST getLeftChild() {
    return new EmptyAST();
  }

  @Override
  public AST getRightChild() {
    return new EmptyAST();
  }

  @Override
  public boolean isLeaf() {
    return true;
  }

  @Override
  public AST addAST(AST ast) {
    return ast;
  }

  @Override
  public void setToken(Token token) {}

  @Override
  public void setLeftChild(AST ast) {}

  @Override
  public void setRightChild(AST ast) {}

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public ContextBuilder executeTree(ContextBuilder context) {
    // TODO
    return context;
  }
}
