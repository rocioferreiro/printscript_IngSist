package edu.austral.ingsis.branches;

import edu.austral.ingsis.AST;
import edu.austral.ingsis.ContextBuilder;
import edu.austral.ingsis.Token;
import java.util.List;

public class IfAST implements AST {

  private Token token;
  private List<AST> leftChild;
  private List<AST> rightChild;

  public IfAST() {}

  public IfAST(Token token) {
    this.token = token;
  }

  @Override
  public Token getToken() {
    return token;
  }

  @Override
  public List<AST> getLeftIf() {
    return leftChild;
  }

  @Override
  public List<AST> getRightIf() {
    return rightChild;
  }

  @Override
  public boolean isLeaf() {
    return false;
  }

  @Override
  public void setToken(Token token) {
    this.token = token;
  }

  @Override
  public void setLeftIf(List<AST> list) {
    leftChild = list;
  }

  @Override
  public void setRightIf(List<AST> list) {
    rightChild = list;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  public ContextBuilder executeTree(ContextBuilder context) {
    if (context.getVariableValue(token.getValue()).equals("true"))
      return executeIf(leftChild, context);
    else return executeIf(rightChild, context);
  }

  private ContextBuilder executeIf(List<AST> list, ContextBuilder context) {
    for (AST ast : list) {
      context = ast.executeTree(context);
      context.build();
    }
    return context;
  }
}
