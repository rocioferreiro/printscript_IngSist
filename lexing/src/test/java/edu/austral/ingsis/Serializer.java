package edu.austral.ingsis;

import java.util.List;
import java.util.stream.Collectors;

public class Serializer {

  public static List<String> serialize(List<Token> tokens) {
    return tokens.stream().map(Serializer::tokenToString).collect(Collectors.toList());
  }

  private static String tokenToString(Token token) {
    return "(" + token.getType() + ", " + token.getValue() + ")";
  }
}
