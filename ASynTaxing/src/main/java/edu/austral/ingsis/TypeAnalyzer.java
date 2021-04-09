package edu.austral.ingsis;

public class TypeAnalyzer {

  public static int getTreeType(AST ast, ContextBuilder contextBuilder) {
    int ordinal;
    if (ast.isLeaf()) {
      return ast.getToken().getType().getOrdinal() > KeyWord.STRING.getOrdinal()
                      ? contextBuilder.getVariableType(ast.getToken().getValue()).getOrdinal()
                      : ast.getToken().getType().getOrdinal();
    }
    Token leftToken = ast.getLeftChild().getToken();
    Token rightToken = ast.getRightChild().getToken();
    if (ast.getLeftChild().isLeaf() && ast.getRightChild().isLeaf()) {
      ordinal = Integer.max(leftToken.getType().getOrdinal(), rightToken.getType().getOrdinal());
      if (ordinal > KeyWord.STRING.getOrdinal()) {
        int ordinal1 =
            leftToken.getType().getOrdinal() > KeyWord.STRING.getOrdinal()
                ? contextBuilder.getVariableType(leftToken.getValue()).getOrdinal()
                : leftToken.getType().getOrdinal();
        int ordinal2 =
            rightToken.getType().getOrdinal() > KeyWord.STRING.getOrdinal()
                ? contextBuilder.getVariableType(rightToken.getValue()).getOrdinal()
                : rightToken.getType().getOrdinal();
        return Integer.max(ordinal1, ordinal2);
      }
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
