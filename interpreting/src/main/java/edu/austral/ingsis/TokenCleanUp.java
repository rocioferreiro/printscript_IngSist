package edu.austral.ingsis;

import java.util.List;

public class TokenCleanUp {

  public static void checkLastToken(List<Token> tokens) {
    Token lastToken = tokens.get(tokens.size() - 1);
    if (!lastToken.getType().equals(Operator.SEMICOLONS)) {
      throw new InvalidCodeException("Missing last semicolon.", lastToken.getPosition());
    }
  }

  public static List<Token> checkLastTokenAndRemove(List<Token> tokens) {
    checkLastToken(tokens);
    return tokens.subList(0, tokens.size()-1);
  }

  public static int getIndexOfNextSemicolon(List<Token> tokens) {
    for (int i = 0; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(Operator.SEMICOLONS)) {
        return i;
      }
    }
    return -1;
  }
}
