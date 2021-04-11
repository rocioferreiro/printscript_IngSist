package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public enum Operator implements TokenType {
  EQUAL("=", "EQUAL"),
  T_ASSIGNATION(":", "DECLARE_TYPE"),
  EQUAL_EQUAL("\\==", "COMPARATOR"),
  GREATER_EQUAL("\\>=", "COMPARATOR"),
  MINOR_EQUAL("\\<=", "COMPARATOR"),
  GREATER("\\>", "COMPARATOR"),
  MINOR("\\<", "COMPARATOR"),
  PLUS("\\+", "OPERATOR"),
  HYPHEN("-", "OPERATOR"),
  DASH("\\/", "OPERATOR"),
  ASTERISK("\\*", "OPERATOR"),
  L_PARENTHESIS("\\(", "L_PARENTHESIS"),
  R_PARENTHESIS("\\)", "R_PARENTHESIS"),
  SEMICOLONS(";", "SEPARATOR");

  private final String regex;
  private final String category;

  Operator(String regex, String category) {
    this.regex = regex;
    this.category = category;
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

  public static List<Token> findTokens(String string, Position initialPosition) {
    List<Token> finalList = new ArrayList<>();
    if (string.isEmpty()) return finalList;
    int i = 0;
    boolean match = false;
    for (Operator key : values()) {
      if(string.matches(".*" + key.getRegex() + ".*")) {
        match = true;
        String[] split = split(string, key.getRegex());
        finalList.addAll(findTokens(split[0], initialPosition.incrementColumn(i)));
        i += split[0].length();
        finalList.add(new ConcreteToken(key, key.getRegex().replace("\\", ""), initialPosition.incrementColumn(i)));
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
    String s2 = index == string.length()-1 ? "" : string.substring(index+regex.length());
    return new String[]{s1, s2};
  }
}
