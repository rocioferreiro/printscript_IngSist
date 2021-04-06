package edu.austral.ingsis.rules;

import edu.austral.ingsis.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConcreteRule implements Rule {

  private final RuleType type;
  private final String acceptingRegex;

  public ConcreteRule(RuleType type, String acceptingRegex) {
    this.type = type;
    this.acceptingRegex = acceptingRegex;
  }

  @Override
  public Optional<ASTWrapper> validateTokens(List<Token> list) {
    String concat =
        list.stream().map(t -> t.getType().getCategory()).collect(Collectors.joining(","));
    if (concat.matches(acceptingRegex)) {
      Collections.reverse(list);
      AST aux = TokenToASTConverter.convert(list.get(0));
      for (int i = 1; i < list.size(); i++) {
        aux = TokenToASTConverter.convert(list.get(i)).addAST(aux);
      }
      return Optional.of(new ASTWrapper(aux, type));
    }
    return Optional.empty();
  }

  @Override
  public RuleType getRuleType() {
    return type;
  }

  @Override
  public String getAcceptingRegex() {
    return acceptingRegex;
  }
}
