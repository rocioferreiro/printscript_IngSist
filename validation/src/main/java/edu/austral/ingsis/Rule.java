package edu.austral.ingsis;

import java.util.List;

public interface Rule {
  boolean validateTokens(List<Token> list);

  RuleType getRuleType();

  String getAcceptingRegex();
}
