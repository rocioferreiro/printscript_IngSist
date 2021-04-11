package edu.austral.ingsis.branches;

import edu.austral.ingsis.*;

public class AssignationAST implements ASTBranch {

  private Token token;
  private AST leftChild = new EmptyAST();
  private AST rightChild = new EmptyAST();

  @Override
  public Token getToken() {
    return token;
  }

  @Override
  public AST getLeftChild() {
    return leftChild;
  }

  @Override
  public AST getRightChild() {
    return rightChild;
  }

  @Override
  public AST addAST(AST ast) {
    setRightChild(ast);
    return this;
  }

  @Override
  public void setToken(Token token) {
    this.token = token;
  }

  @Override
  public void setLeftChild(AST ast) {
    this.leftChild = ast;
  }

  @Override
  public void setRightChild(AST ast) {
    this.rightChild = ast;
  }

  @Override
  public ContextBuilder executeTree(ContextBuilder context) {
    context = leftChild.executeTree(context);
    if (leftChild.isLeaf() && context.toAddIsConst()) {
      throw new InvalidCodeException("You can't re-assign a constant", leftChild.getToken().getPosition());
    }
    return rightChild.executeTree(context);
  }
}
