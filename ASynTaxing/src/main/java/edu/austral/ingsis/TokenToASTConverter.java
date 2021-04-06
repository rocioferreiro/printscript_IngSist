package edu.austral.ingsis;

import java.util.HashMap;
import java.util.Map;

public class TokenToASTConverter {

  private static Map<TokenType, AST> table =
      new HashMap<>() {
        {
          put(Operator.PLUS, new OperationAST());
          put(Operator.EQUAL, new AssignationAST());
          put(Operator.ASTERISK, new OperationAST());
          put(Operator.DASH, new OperationAST());
          put(Operator.HYPHEN, new OperationAST());
          put(Operator.L_PARENTHESIS, new EmptyAST());
          put(Operator.R_PARENTHESIS, new EmptyAST());
          put(Operator.T_ASSIGNATION, new DeclarationAST());
          put(Operator.SEMICOLONS, new EmptyAST());
          put(KeyWord.DECLARATION, new EmptyAST());
          put(KeyWord.STRING, new LiteralAST());
          put(KeyWord.NUMBER, new LiteralAST());
          put(KeyWord.N_ASSIGNATION, new NumberAssignationAST());
          put(KeyWord.S_ASSIGNATION, new StringAssignationAST());
          put(KeyWord.VARIABLE_REF, new VariableAST());
          put(KeyWord.PRINTLN, new MethodAST());
        }
      };

  public static AST convert(Token token) {
    AST tree = table.get(token.getType());
    tree.setToken(token);
    return tree;
  }
}
