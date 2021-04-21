package edu.austral.ingsis.rules;

import edu.austral.ingsis.ASTWrapper;
import edu.austral.ingsis.Token;
import java.util.List;
import java.util.Optional;

public interface Rule {
  Optional<ASTWrapper> validateTokens(List<Token> list);

  RuleType getRuleType();

  String getAcceptingRegex();

  default void setContextApprovedRules(List<Rule> contextApprovedRules) {}
}
