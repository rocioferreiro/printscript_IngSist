package edu.austral.ingsis;

public interface AST {

  Token getToken();

  AST getLeftChild();

  AST getRightChild();

  boolean isLeaf();

  AST addAST(AST ast);

  void setToken(Token token);

  void setLeftChild(AST ast);

  void setRightChild(AST ast);

  boolean isEmpty();

  ContextBuilder executeTree(ContextBuilder context);


}
