package edu.austral.ingsis.branches;

import edu.austral.ingsis.AST;
import edu.austral.ingsis.ContextBuilder;
import edu.austral.ingsis.EmptyAST;
import edu.austral.ingsis.Token;

public class GreaterEqualAST implements ASTBranch {

  private Token token;
  private AST leftChild = new EmptyAST();
  private AST rightChild = new EmptyAST();

  public GreaterEqualAST(Token token) {
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
    this.leftChild = ast;
  }

  @Override
  public void setRightChild(AST ast) {
    this.rightChild = ast;
  }

  @Override
  public ContextBuilder executeTree(ContextBuilder context) {
    String left = leftChild.executeTree(context).getToAddValue();
    String right = rightChild.executeTree(context).getToAddValue();
    int compare = Integer.valueOf(left).compareTo(Integer.valueOf(right));
    if (compare >= 0) return context.setToAddValue("true");
    else return context.setToAddValue("false");
  }
}
