package edu.austral.ingsis.leaves;

import edu.austral.ingsis.AST;
import edu.austral.ingsis.EmptyAST;
import edu.austral.ingsis.InvalidCodeException;

public interface ASTLeaf extends AST {

  @Override
  default AST getLeftChild() {
    return new EmptyAST();
  }

  @Override
  default AST getRightChild() {
    return new EmptyAST();
  }

  @Override
  default boolean isLeaf() {
    return true;
  }

  @Override
  default boolean isEmpty() {
    return false;
  }

  @Override
  default void setLeftChild(AST ast) {}

  @Override
  default void setRightChild(AST ast) {}

  @Override
  default AST addAST(AST ast) {
    if (ast.isEmpty()) return this;
    if (ast.isLeaf())
      throw new InvalidCodeException("invalid phrase", this.getToken().getPosition());
    if (ast.getLeftChild().isEmpty()) {
      ast.setLeftChild(this);
      return ast;
    }
    ast.setLeftChild(addAST(ast.getLeftChild()));
    return ast;
  }
}
