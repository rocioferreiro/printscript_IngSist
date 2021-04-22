package edu.austral.ingsis.branches;

import edu.austral.ingsis.*;

public class PrintAST implements ASTBranch {

  private Token token;
  private AST leftChild = new EmptyAST();
  private AST rightChild = new EmptyAST();

  public PrintAST(Token token) {
    this.token = token;
  }

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
    if (ast.isLeaf()) {
      setRightChild(ast);
      return this;
    }
    rightChild = ast;
    return this;
  }

  @Override
  public void setToken(Token token) {
    this.token = token;
  }

  @Override
  public void setLeftChild(AST ast) {
    leftChild = ast;
  }

  @Override
  public void setRightChild(AST ast) {
    rightChild = ast;
  }

  @Override
  public ContextBuilder executeTree(ContextBuilder context) {
    context = rightChild.executeTree(context);
    String toPrint = context.getToAddValue();
    ContextBuilder finalContext = context;
    return context.setNextExecute(
        () -> {
          finalContext.getOut().accept(toPrint);
        });
  }
}
