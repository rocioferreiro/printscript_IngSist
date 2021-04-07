package edu.austral.ingsis.branches;

import edu.austral.ingsis.*;

public class AsteriskAST implements ASTBranch {

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
    if (leftType <= KeyWord.NUMBER.getOrdinal() && rightType <= KeyWord.NUMBER.getOrdinal())
      return context.setToAddValue(
          String.valueOf(Double.parseDouble(left) * Double.parseDouble(right)));
    if (leftType <= KeyWord.NUMBER.getOrdinal()) {
      int leftInt = (int) Double.parseDouble(left);
      if (leftInt > right.length()) return context.setToAddValue("");
      return context.setToAddValue(right.repeat(leftInt));
    }
    if (rightType <= KeyWord.NUMBER.getOrdinal()) {
      int rightInt = (int) Double.parseDouble(right);
      if (rightInt > left.length()) return context.setToAddValue("");
      return context.setToAddValue(right.repeat(rightInt));
    }
    return context.setToAddValue(mergeStrings(left, right));
  }

  private String mergeStrings(String s1, String s2) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < s1.length() || i < s2.length(); i++) {
      if (i < s1.length()) result.append(s1.charAt(i));

      if (i < s2.length()) result.append(s2.charAt(i));
    }

    return result.toString();
  }
}
