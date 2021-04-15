package edu.austral.ingsis;

import edu.austral.ingsis.rules.RuleType;

import java.util.List;

public interface ASTWrapper {

  AST getTree();
  RuleType getType();
  default List<ASTWrapper> getLeft() {return null;}
  default List<ASTWrapper> getRight() {return null;}
}
