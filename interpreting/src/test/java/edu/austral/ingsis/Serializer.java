package edu.austral.ingsis;

import java.util.List;
import java.util.stream.Collectors;

public class Serializer {

  public static List<String> serializeTokens(List<Token> tokens) {
    return tokens.stream().map(Serializer::tokenToString).collect(Collectors.toList());
  }

  public static List<String> serializeLines(List<Line> lines) {
    return lines.stream().map(Serializer::lineToString).collect(Collectors.toList());
  }

  private static String tokenToString(Token token) {
    return "(" + token.getType() + ", " + token.getValue() + ")";
  }

  private static String lineToString(Line line) {
    return "(" + line.getText() + ", " + line.getRow() + ")";
  }
}
