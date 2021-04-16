package edu.austral.ingsis;

import java.util.List;

public class TokenCleanUp {

  public static void checkLastToken(List<Token> tokens) {
    Token lastToken = tokens.get(tokens.size() - 1);
    if (!lastToken.getType().equals(Operator.SEMICOLONS)
        && !lastToken.getType().equals(Operator.R_KEY)) {
      throw new InvalidCodeException("Missing separator.", lastToken.getPosition());
    }
  }

  public static List<Token> checkLastTokenAndRemove(List<Token> tokens) {
    checkLastToken(tokens);
    if (tokens.get(tokens.size() - 1).getType().equals(Operator.R_KEY)) return tokens;
    return tokens.subList(0, tokens.size() - 1);
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

  public static int getAmountOfSentences(List<Token> tokens) {
    int counter = 0;
    for (Token token : tokens) {
      if (token.getType().equals(Operator.SEMICOLONS)) counter++;
    }
    return counter;
  }
}
