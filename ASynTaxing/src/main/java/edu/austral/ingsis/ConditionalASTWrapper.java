package edu.austral.ingsis;

import edu.austral.ingsis.rules.RuleType;

import java.util.List;

public class ConditionalASTWrapper implements ASTWrapper {

  private final AST ast;
  private final List<ASTWrapper> left;
  private final List<ASTWrapper> right;
  private final RuleType type;

  public ConditionalASTWrapper(AST ast, List<ASTWrapper> left, List<ASTWrapper> right, RuleType type) {
    this.ast = ast;
    this.left = left;
    this.right = right;
    this.type = type;
  }

  @Override
  public AST getTree() {
    return ast;
  }

  @Override
  public RuleType getType() {
    return type;
  }

  @Override
  public List<ASTWrapper> getLeft() {
    return left;
  }

  @Override
  public List<ASTWrapper> getRight() {
    return right;
  }
}
