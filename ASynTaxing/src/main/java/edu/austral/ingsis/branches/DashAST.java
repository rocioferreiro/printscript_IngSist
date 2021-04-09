package edu.austral.ingsis.branches;

import edu.austral.ingsis.*;

public class DashAST implements ASTBranch {

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
    int rightType = TypeAnalyzer.getTreeType(rightChild, context);
    String left = leftChild.executeTree(context).getToAddValue();
    String right = rightChild.executeTree(context).getToAddValue();
    if (leftType <= KeyWord.NUMBER.getOrdinal() && rightType <= KeyWord.NUMBER.getOrdinal()) {
      double rightInt = Double.parseDouble(right);
      if (rightInt == 0)
        throw new InvalidCodeException("Division by zero /0", leftChild.getToken().getPosition());
      return context.setToAddValue(
          String.valueOf(Double.parseDouble(left) / rightInt));
    }
    if (leftType <= KeyWord.NUMBER.getOrdinal()) {
      int leftInt = (int) Double.parseDouble(left);
      return context.setToAddValue(divideStringByNumber(right, leftInt));
    }
    if (rightType <= KeyWord.NUMBER.getOrdinal()) {
      int rightInt = (int) Double.parseDouble(right);
      return context.setToAddValue(divideStringByNumber(left, rightInt));
    }
    return context.setToAddValue(left.replaceAll(right, " "));
  }

  private String divideStringByNumber(String string, int number) {
    String result = "";
    String aux = string;
    for (int i = 0; i < string.length() - number; i += number) {
      result += string.substring(i, i + number) + " ";
      aux = aux.substring(i+number);
    }
    return result + aux;
  }
}
