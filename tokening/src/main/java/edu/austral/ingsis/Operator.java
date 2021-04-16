package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public enum Operator implements TokenType {
  T_ASSIGNATION(":", "DECLARE_TYPE", true),
  EQUAL_EQUAL("==", "COMPARATOR", false),
  GREATER_EQUAL(">=", "COMPARATOR", false),
  MINOR_EQUAL("<=", "COMPARATOR", false),
  EQUAL("=", "EQUAL", true),
  GREATER(">", "COMPARATOR", false),
  MINOR("<", "COMPARATOR", false),
  PLUS("\\+", "OPERATOR", true),
  HYPHEN("-", "OPERATOR", true),
  DASH("\\/", "OPERATOR", true),
  ASTERISK("\\*", "OPERATOR", true),
  L_PARENTHESIS("\\(", "L_PARENTHESIS", true),
  R_PARENTHESIS("\\)", "R_PARENTHESIS", true),
  L_KEY("\\{", "L_KEY", false),
  R_KEY("\\}", "R_KEY", false),
  SEMICOLONS(";", "SEPARATOR", true);

  private final String regex;
  private final String category;
  private boolean isAble;

  Operator(String regex, String category, boolean isAble) {
    this.regex = regex;
    this.category = category;
    this.isAble = isAble;
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public String getCategory() {
    return category;
  }

  @Override
  public String getName() {
    return name().toLowerCase(Locale.ROOT);
  }

  @Override
  public int getOrdinal() {
    return ordinal();
  }

  @Override
  public boolean isAble() {
    return isAble;
  }

  @Override
  public void setAble(boolean isAble) {
    this.isAble = isAble;
  }

  public static List<Token> findTokens(String string, Position initialPosition) {
    List<Token> finalList = new ArrayList<>();
    if (string.isEmpty()) return finalList;
    int i = 0;
    boolean match = false;
    for (Operator key : values()) {
      if (string.matches(".*" + key.getRegex() + ".*") && key.isAble()) {
        match = true;
        String[] split = split(string, key.getRegex());
        finalList.addAll(findTokens(split[0], initialPosition.incrementColumn(i)));
        i += split[0].length();
        finalList.add(
            new ConcreteToken(
                key, key.getRegex().replace("\\", ""), initialPosition.incrementColumn(i)));
        i += key.getRegex().length();
        finalList.addAll(findTokens(split[1], initialPosition.incrementColumn(i)));
        break;
      }
    }
    if (!match) {
      finalList.add(new ProvisionalToken(string, initialPosition));
    }
    return finalList;
  }

  private static String[] split(String string, String regex) {
    regex = regex.replace("\\", "");
    int index = string.indexOf(regex);
    String s1 = string.substring(0, index);
    String s2 = index == string.length() - 1 ? "" : string.substring(index + regex.length());
    return new String[] {s1, s2};
  }
}
