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

  public static boolean contains(List<Token> tokens, TokenType type) {
    for (Token token : tokens) if (token.getType().equals(type)) return true;
    return false;
  }

  private static int getIndexOfNextSeparatorConditional(
      List<Token> tokens, int indexOfConditional) {
    int provisionalEndIndex = -1;
    for (int i = indexOfConditional; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(Operator.L_KEY)) {
        provisionalEndIndex = getEndIndex(tokens, i + 1);
        break;
      }
    }
    if (provisionalEndIndex < tokens.size() - 1
        && tokens.get(provisionalEndIndex + 1).getType().equals(KeyWord.ELSE_STATEMENT))
      return getIndexOfNextSeparatorConditional(tokens, provisionalEndIndex);
    return provisionalEndIndex;
  }

  public static int getAmountOfSentences(List<Token> tokens) {
    int counter = 0;

    for (int i = 0; i < tokens.size(); i++) {

      if (tokens.get(i).getType().equals(KeyWord.IF_STATEMENT)) {
        i = getIndexOfNextSeparatorConditional(tokens, i + 1);
        counter++;
      }
      if (tokens.get(i).getType().equals(Operator.SEMICOLONS)) counter++;
    }
    return counter;
  }

  private static int getEndIndex(List<Token> tokens, int initialIndex) {
    int leftKeyCounter = 0;
    for (int j = initialIndex; j < tokens.size(); j++) {
      if (tokens.get(j).getType().equals(Operator.L_KEY)) leftKeyCounter++;
      if (tokens.get(j).getType().equals(Operator.R_KEY)) {
        if (leftKeyCounter > 0) leftKeyCounter--;
        else return j;
      }
    }
    return 0;
  }

  public static void activateTokens() {
    TokenType[] types = {
      KeyWord.B_ASSIGNATION,
      KeyWord.C_DECLARATION,
      KeyWord.BOOLEAN,
      KeyWord.IF_STATEMENT,
      KeyWord.ELSE_STATEMENT,
      Operator.EQUAL_EQUAL,
      Operator.GREATER_EQUAL,
      Operator.MINOR_EQUAL,
      Operator.GREATER,
      Operator.MINOR,
      Operator.L_KEY,
      Operator.R_KEY
    };
    for (TokenType t : types) t.setAble(true);
  }
}
