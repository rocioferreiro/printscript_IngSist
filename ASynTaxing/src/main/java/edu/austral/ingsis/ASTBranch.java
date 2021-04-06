package edu.austral.ingsis;

public interface ASTBranch extends AST {

  @Override
  default boolean isLeaf() {
    return false;
  }

  @Override
  default boolean isEmpty() {
    return false;
  }

  @Override
  default AST addAST(AST ast) {
    if (ast.isLeaf()) {
      setRightChild(ast);
      return this;
    }
    ast.setLeftChild(addAST(ast.getLeftChild()));
    return ast;
  }
}
