package edu.austral.ingsis;

import java.util.Optional;

public interface AST {

  Token getToken();

  Optional<AST> getLeftChild();

  Optional<AST> getRightChild();

  AST addAST(AST ast);
}
