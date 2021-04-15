package edu.austral.ingsis.rules;

import edu.austral.ingsis.*;
import edu.austral.ingsis.branches.IfAST;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConditionalRule implements Rule {

  private final String acceptingRegex;
  private final RuleType type;
  private static final int conditionPosition = 2;
  private List<Rule> contextApprovedRules;

  public ConditionalRule(RuleType type, String acceptingRegex) {
    this.acceptingRegex = acceptingRegex;
    this.type = type;
    contextApprovedRules = new ArrayList<>();
  }

  public void setContextApprovedRules(List<Rule> contextApprovedRules) {
    this.contextApprovedRules = contextApprovedRules;
  }

  @Override
  public Optional<ASTWrapper> validateTokens(List<Token> list) {
    String concat =
        list.stream().map(t -> t.getType().getCategory()).collect(Collectors.joining(","));
    if (concat.matches(acceptingRegex)) {
      AST ifAst = new IfAST(list.get(conditionPosition));
      List<ASTWrapper> ifAsts = getAsts(getBetweenKeysOfIf(list));
      List<ASTWrapper> elseAsts = getAsts(getBetweenKeysOfElse(list));
      return Optional.of(new ConditionalASTWrapper(ifAst, ifAsts, elseAsts, type));
    }
    return Optional.empty();
  }

  private List<ASTWrapper> getAsts(List<Token> tokens) {
    List<ASTWrapper> resultList = new ArrayList<>();
    int amount = TokenCleanUp.getAmountOfSentences(tokens);
    List<Token> sublist = tokens;
    for (int i = 0; i < amount; i++) {
      int nextIndex = getIndexOfNextSeparator(sublist);
      final Position pos = sublist.get(0).getPosition();
      for (Rule rule : contextApprovedRules) {
        Optional<ASTWrapper> result =
            rule.validateTokens(new ArrayList<>(sublist.subList(0, nextIndex)));
        result.ifPresent(resultList::add);
      }
      if (resultList.isEmpty()) throw new InvalidCodeException("Invalid syntax", pos);
      sublist = new ArrayList<>(sublist.subList(nextIndex + 1, sublist.size()));
    }
    return resultList;
  }

  public static int getIndexOfNextSeparator(List<Token> tokens) {
    for (int i = 0; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(KeyWord.IF_STATEMENT)) {
        return getIndexOfNextSeparatorConditional(tokens, i);
      }
      if (tokens.get(i).getType().equals(Operator.SEMICOLONS)) {
        return i;
      }
    }
    return -1;
  }

  private static int getIndexOfNextSeparatorConditional(
      List<Token> tokens, int indexOfConditional) {
    for (int i = indexOfConditional; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(KeyWord.ELSE_STATEMENT)) {
        for (int j = i + 1; j < tokens.size(); j++) {
          if (tokens.get(j).getType().equals(Operator.R_KEY)) {
            return j;
          }
        }
      }
    }
    for (int i = indexOfConditional; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(Operator.L_KEY)) {
        for (int j = i + 1; j < tokens.size(); j++) {
          if (tokens.get(j).getType().equals(Operator.R_KEY)) {
            return j;
          }
        }
      }
    }
    return -1;
  }

  private List<Token> getBetweenKeysOfIf(List<Token> tokens) {
    int initialIndex = 0;
    int endIndex = 0;
    for (int i = 0; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(Operator.L_KEY)) {
        initialIndex = i + 1;
        endIndex = getEndIndex(tokens, initialIndex);
        break;
      }
    }
    return tokens.subList(initialIndex, endIndex);
  }

  private List<Token> getBetweenKeysOfElse(List<Token> tokens) {
    int inicialIndex = 0, endIndex = 0;
    for (int i = 0; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(KeyWord.ELSE_STATEMENT)) {
        inicialIndex = i + 2;
        endIndex = getEndIndex(tokens, inicialIndex);
        break;
      }
    }
    return tokens.subList(inicialIndex, endIndex);
  }

  private int getEndIndex(List<Token> tokens, int inicialIndex) {
    int leftKeyCounter = 0;
    for (int j = inicialIndex; j < tokens.size(); j++) {
      if (tokens.get(j).getType().equals(Operator.L_KEY)) leftKeyCounter++;
      if (tokens.get(j).getType().equals(Operator.R_KEY)) {
        if (leftKeyCounter > 0) leftKeyCounter--;
        else return j;
      }
    }
    return 0;
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
