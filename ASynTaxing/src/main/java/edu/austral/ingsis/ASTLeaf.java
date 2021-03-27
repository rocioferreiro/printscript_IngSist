package edu.austral.ingsis;

import java.util.Optional;

public interface ASTLeaf extends AST {

  @Override
  default Optional<AST> getLeftChild() {
    return Optional.empty();
  }

  @Override
  default Optional<AST> getRightChild() {
    return Optional.empty();
  }
}
