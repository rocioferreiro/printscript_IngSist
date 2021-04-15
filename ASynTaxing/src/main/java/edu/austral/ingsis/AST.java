package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public interface AST {

  Token getToken();

  default AST getLeftChild() {
    return new EmptyAST();
  }

  default AST getRightChild() {
    return new EmptyAST();
  }

  default List<AST> getLeftIf() {
    return new ArrayList<>();
  }

  default List<AST> getRightIf() {
    return new ArrayList<>();
  }

  boolean isLeaf();

  default AST addAST(AST ast) {return null;};

  void setToken(Token token);

  default void setLeftChild(AST ast) {}

  default void setRightChild(AST ast) {}

  default void setLeftIf(List<AST> list) {}

  default void setRightIf(List<AST> list) {}

  boolean isEmpty();

  ContextBuilder executeTree(ContextBuilder context);
}
