package edu.austral.ingsis.branches;

import edu.austral.ingsis.*;

public class DeclarationAST implements ASTBranch {

  private Token token;
  private AST leftChild = new EmptyAST();
  private AST rightChild = new EmptyAST();

  public DeclarationAST(Token token) {
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
    Variable var = context.getVariable(leftChild.getToken().getValue());
    return context
        .addVariable(new VariableBuilder())
        .setToAddName(var.getName())
        .setToAddType(
            new VariableType(
                rightChild.getToken().getValue(), rightChild.getToken().getType().getOrdinal()))
        .setToAddIsConst(var.isConst());
  }
}
