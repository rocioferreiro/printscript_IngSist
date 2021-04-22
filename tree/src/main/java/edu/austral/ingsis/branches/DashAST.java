package edu.austral.ingsis.branches;

import edu.austral.ingsis.*;

public class DashAST implements ASTBranch {

  private Token token;
  private AST leftChild = new EmptyAST();
  private AST rightChild = new EmptyAST();

  public DashAST(Token token) {
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
    int leftType = TypeAnalyzer.getTreeType(leftChild, context);
    int rightType = TypeAnalyzer.getTreeType(rightChild, context);
    String left = leftChild.executeTree(context).getToAddValue();
    String right = rightChild.executeTree(context).getToAddValue();
    if (leftType <= KeyWord.NUMBER.getOrdinal() && rightType <= KeyWord.NUMBER.getOrdinal()) {
      double rightInt = Double.parseDouble(right);
      if (rightInt == 0.0)
        throw new InvalidCodeException("Division by zero /0", leftChild.getToken().getPosition());
      return context.setToAddValue(String.valueOf(Double.parseDouble(left) / rightInt));
    }
    throw new InvalidCodeException("Can't operate with string", token.getPosition());
  }
}
