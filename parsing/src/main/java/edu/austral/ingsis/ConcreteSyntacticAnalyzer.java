package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
  public Sentence analyze(List<Token> tokens) {
    RuleType type = checkRules(tokens);
    return new Sentence(tokens, type);
  }

  private RuleType checkRules(List<Token> tokens) {
    for (Rule rule : rules) {
      if (rule.validateTokens(tokens)) return rule.getRuleType();
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
