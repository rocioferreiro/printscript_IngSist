package edu.austral.ingsis;

public class TypeAnalyzer {

  public static int getTreeType(AST ast, ContextBuilder contextBuilder) {
    int ordinal;
    Token leftToken = ast.getLeftChild().getToken();
    Token rightToken = ast.getRightChild().getToken();
    if (ast.getLeftChild().isLeaf() && ast.getRightChild().isLeaf()) {
      ordinal = Integer.max(leftToken.getType().getOrdinal(), rightToken.getType().getOrdinal());
      if (ordinal > KeyWord.STRING.getOrdinal())
        return Integer.max(
            contextBuilder.getVariableType(leftToken.getValue()).getOrdinal(),
            contextBuilder.getVariableType(rightToken.getValue()).getOrdinal());
    } else if (ast.getLeftChild().isLeaf()) {
      ordinal =
          Integer.max(
              leftToken.getType().getOrdinal(), getTreeType(ast.getRightChild(), contextBuilder));
      if (ordinal > KeyWord.STRING.getOrdinal())
        return contextBuilder.getVariableType(leftToken.getValue()).getOrdinal();
    } else if (ast.getRightChild().isLeaf()) {
      ordinal =
          Integer.max(
              rightToken.getType().getOrdinal(), getTreeType(ast.getLeftChild(), contextBuilder));
      if (ordinal > KeyWord.STRING.getOrdinal())
        return contextBuilder.getVariableType(rightToken.getValue()).getOrdinal();
    } else
      ordinal =
          Integer.max(
              getTreeType(ast.getRightChild(), contextBuilder),
              getTreeType(ast.getLeftChild(), contextBuilder));

    return ordinal;
  }
}
