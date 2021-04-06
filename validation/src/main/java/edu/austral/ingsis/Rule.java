package edu.austral.ingsis;

import java.util.List;
import java.util.Optional;

public interface Rule {
  Optional<ASTWrapper> validateTokens(List<Token> list);

  RuleType getRuleType();

  String getAcceptingRegex();
}
