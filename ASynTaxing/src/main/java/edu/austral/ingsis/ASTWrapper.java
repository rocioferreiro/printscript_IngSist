package edu.austral.ingsis;

import edu.austral.ingsis.rules.RuleType;

public class ASTWrapper {

  private final AST tree;
  private final RuleType type;

  public ASTWrapper(AST tree, RuleType type) {
    this.tree = tree;
    this.type = type;
  }

  public AST getTree() {
    return tree;
  }

  public RuleType getType() {
    return type;
  }
}
