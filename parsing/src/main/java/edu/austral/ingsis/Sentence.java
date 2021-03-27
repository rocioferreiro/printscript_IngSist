package edu.austral.ingsis;

import java.util.List;

public class Sentence {

  private final List<Token> tokens;
  private final RuleType type;

  public Sentence(List<Token> tokens, RuleType type) {
    this.tokens = tokens;
    this.type = type;
  }

  public List<Token> getTokens() {
    return tokens;
  }

  public RuleType getType() {
    return type;
  }
}
