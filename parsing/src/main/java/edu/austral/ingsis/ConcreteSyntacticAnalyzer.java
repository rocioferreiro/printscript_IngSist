package edu.austral.ingsis;

import edu.austral.ingsis.rules.ConcreteRule;
import edu.austral.ingsis.rules.Rule;
import edu.austral.ingsis.rules.RuleType;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConcreteSyntacticAnalyzer implements SyntacticAnalyzer {

  private final List<Rule> rules;

  public ConcreteSyntacticAnalyzer(Path rulePath) {
    List<Rule> rulesToAdd = new ArrayList<>();
    String[] ruleTexts = PathReader.read(rulePath).split("\n").clone();
    for (String rule : ruleTexts) {
      String[] array = rule.split(":");
      RuleType type = RuleType.ruleOfId(Integer.parseInt(array[0]));
      rulesToAdd.add(new ConcreteRule(type, array[1]));
    }
    rules = rulesToAdd;
  }

  @Override
  public ASTWrapper analyze(List<Token> tokens) {
    Optional<ASTWrapper> result;
    for (Rule rule : rules) {
      result = rule.validateTokens(tokens);
      if (result.isPresent()) return result.get();
    }
    String expresion = tokens.stream().map(Token::getValue).collect(Collectors.joining(" "));
    String types =
        tokens.stream().map(t -> t.getType().getCategory()).collect(Collectors.joining(" "));
    String ruleString =
        rules.stream().map(Rule::getAcceptingRegex).collect(Collectors.joining(",\n\t"));
    throw new InvalidCodeException(
        "Invalid Expresion: "
            + expresion
            + "\nThis sequence of types is not allowed: "
            + types
            + "\nTry one of the following: \n\t"
            + ruleString,
        tokens.get(0).getPosition());
  }
}
