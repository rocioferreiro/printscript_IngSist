package edu.austral.ingsis.branches;

import edu.austral.ingsis.*;

public class HyphenAST implements ASTBranch {

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
    int leftType = TypeAnalyzer.getTreeType(leftChild, context);
    int rightType = TypeAnalyzer.getTreeType(leftChild, context);
    String left = leftChild.executeTree(context).getToAddValue();
    String right = rightChild.executeTree(context).getToAddValue();
    if(leftType <= KeyWord.NUMBER.getOrdinal() && rightType <= KeyWord.NUMBER.getOrdinal())
      return context.setToAddValue(String.valueOf(Double.parseDouble(left) - Double.parseDouble(right)));
    if(leftType <= KeyWord.NUMBER.getOrdinal()) {
      int leftInt = (int) Double.parseDouble(left);
      if(leftInt > right.length()) return context.setToAddValue("");
      return context.setToAddValue(right.substring(0, right.length()-leftInt));
    }
    if(rightType <= KeyWord.NUMBER.getOrdinal()){
      int rightInt = (int) Double.parseDouble(right);
      if(rightInt > left.length()) return context.setToAddValue("");
      return context.setToAddValue(right.substring(0, left.length()-rightInt));
    }
    return context.setToAddValue(left.replaceAll(right, ""));
  }
}
