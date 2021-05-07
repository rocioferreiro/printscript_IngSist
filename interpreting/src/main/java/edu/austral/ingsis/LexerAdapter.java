package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexerAdapter {

  private static final Map<token.TokenType, TokenType> convert = new HashMap<>() {{
    put(token.TokenType.STRING, KeyWord.STRING);
    put(token.TokenType.NUMBER, KeyWord.NUMBER);
    put(token.TokenType.BOOLEAN, KeyWord.BOOLEAN);
    put(token.TokenType.IDENTIFIER, KeyWord.VARIABLE_REF);
    put(token.TokenType.STRING_TYPE, KeyWord.S_ASSIGNATION);
    put(token.TokenType.NUMBER_TYPE, KeyWord.N_ASSIGNATION);
    put(token.TokenType.BOOLEAN_TYPE, KeyWord.B_ASSIGNATION);
    put(token.TokenType.VARIABLE_KEYWORD, KeyWord.DECLARATION);
    put(token.TokenType.CONSTANT_KEYWORD, KeyWord.C_DECLARATION);
    put(token.TokenType.PLUS_OPERATOR, Operator.PLUS);
    put(token.TokenType.MINUS_OPERATOR, Operator.HYPHEN);
    put(token.TokenType.MULTIPLICATION_OPERATOR, Operator.ASTERISK);
    put(token.TokenType.DIVISION_OPERATOR, Operator.DASH);
    put(token.TokenType.EQUALS, Operator.EQUAL);
    put(token.TokenType.COLON, Operator.T_ASSIGNATION);
    put(token.TokenType.FUNCTION_NAME, KeyWord.PRINTLN);
    put(token.TokenType.LEFT_PARENTHESES, Operator.L_PARENTHESIS);
    put(token.TokenType.RIGHT_PARENTHESES, Operator.R_PARENTHESIS);
    put(token.TokenType.ESC_CHAR, TokenType.invalid);
    put(token.TokenType.SPACE_CHAR, TokenType.invalid);
    put(token.TokenType.IF_FUNCTION, KeyWord.IF_STATEMENT);
    put(token.TokenType.ELSE_FUNCTION, KeyWord.ELSE_STATEMENT);
    put(token.TokenType.LEFT_KEY, Operator.L_KEY);
    put(token.TokenType.RIGHT_KEY, Operator.R_KEY);
    put(token.TokenType.EQUALS_COMPARATOR, Operator.EQUAL_EQUAL);
    put(token.TokenType.GREATER_OR_EQUALS_COMPARATOR, Operator.GREATER_EQUAL);
    put(token.TokenType.GREATER_COMPARATOR, Operator.GREATER);
    put(token.TokenType.MINOR_OR_EQUALS_COMPARATOR, Operator.MINOR_EQUAL);
    put(token.TokenType.MINOR_COMPARATOR, Operator.MINOR);
    put(token.TokenType.IF_BODY, TokenType.invalid);
    put(token.TokenType.IF_BLOCK, TokenType.invalid);
    }
  };

  public static List<Token> adapt(List<token.Token> lex) {
    List<Token> tokens = new ArrayList<>();
    for (token.Token t : lex) {
      tokens.add(new ConcreteToken(convert.get(t.getType()), t.getValue(), new Position(0,0)));
    }
    return tokens;
  }
}
