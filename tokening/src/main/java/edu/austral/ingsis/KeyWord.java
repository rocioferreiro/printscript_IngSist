package edu.austral.ingsis;

import java.util.Locale;

public enum KeyWord implements TokenType {
  DECLARATION("let", "DECLARATION", true),
  C_DECLARATION("const", "C_DECLARATION", false),
  N_ASSIGNATION("number", "TYPE", true),
  NUMBER("\\d+(\\.\\d+)?", "VALUE", true),
  S_ASSIGNATION("string", "TYPE", true),
  STRING("[\"'].*[\"']", "VALUE", true),
  B_ASSIGNATION("boolean", "TYPE", false),
  BOOLEAN("true|false", "VALUE", false),
  PRINTLN("println", "PRINTLN", true),
  IF_STATEMENT("if", "IF", false),
  ELSE_STATEMENT("else", "ELSE", false),
  VARIABLE_REF("[a-zA-Z0-9]+([_a-zA-Z0-9]*)", "VARIABLE", true);

  private final String regex;
  private final String category;
  private boolean isAble;

  KeyWord(String regex, String category, boolean isAble) {
    this.regex = regex;
    this.category = category;
    this.isAble = isAble;
  }

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

  public static Token findToken(Token token) {
    for (KeyWord key : values()) {
      if (token.getValue().matches(key.getRegex())) {
        if (!key.isAble())
          throw new InvalidCodeException(
              "Invalid Expresion: " + token.getValue(), token.getPosition());
        String value = token.getValue();
        if (value.contains("'") || value.contains("\""))
          value = value.substring(1, value.length() - 1);
        return new ConcreteToken(key, value, token.getPosition());
      }
    }
    return token;
  }
}
