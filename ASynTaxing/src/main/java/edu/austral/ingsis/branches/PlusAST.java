package edu.austral.ingsis.branches;

import edu.austral.ingsis.*;

public class PlusAST implements ASTBranch {

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
    String left = leftChild.executeTree(context).getToAddValue();
    String right = rightChild.executeTree(context).getToAddValue();
    if(TypeAnalyzer.getTreeType(this, context) > KeyWord.NUMBER.getOrdinal()) return context.setToAddValue(left + right);
    double leftInt = Double.parseDouble(left);
    double rightInt = Double.parseDouble(right);
    //TODO puede causar problemas
    return context.setToAddValue(String.valueOf(leftInt + rightInt));
  }

}
