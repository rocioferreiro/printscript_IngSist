package edu.austral.ingsis;

import edu.austral.ingsis.branches.*;
import edu.austral.ingsis.leaves.*;
import java.util.HashMap;
import java.util.Map;

public class TokenToASTConverter {

  private static final Map<TokenType, ASTGeneratorCommand> table =
      new HashMap<>() {
        {
          put(Operator.PLUS, PlusAST::new);
          put(Operator.DASH, DashAST::new);
          put(Operator.MINOR, MinorAST::new);
          put(Operator.HYPHEN, HyphenAST::new);
          put(Operator.GREATER, GreaterAST::new);
          put(Operator.SEMICOLONS, EmptyAST::new);
          put(Operator.EQUAL, AssignationAST::new);
          put(Operator.ASTERISK, AsteriskAST::new);
          put(Operator.L_PARENTHESIS, EmptyAST::new);
          put(Operator.R_PARENTHESIS, EmptyAST::new);
          put(Operator.EQUAL_EQUAL, EqualEqualAST::new);
          put(Operator.MINOR_EQUAL, MinorEqualAST::new);
          put(Operator.T_ASSIGNATION, DeclarationAST::new);
          put(Operator.GREATER_EQUAL, GreaterEqualAST::new);

          put(KeyWord.DECLARATION, EmptyAST::new);
          put(KeyWord.STRING, LiteralAST::new);
          put(KeyWord.NUMBER, LiteralAST::new);
          put(KeyWord.BOOLEAN, LiteralAST::new);
          put(KeyWord.N_ASSIGNATION, NumberAssignationAST::new);
          put(KeyWord.S_ASSIGNATION, StringAssignationAST::new);
          put(KeyWord.B_ASSIGNATION, BooleanAssignationAST::new);
          put(KeyWord.C_DECLARATION, EmptyAST::new);
          put(KeyWord.VARIABLE_REF, VariableAST::new);
          put(KeyWord.PRINTLN, PrintAST::new);
        }
      };

  public static AST convert(Token token) {
    AST tree = table.get(token.getType()).create();
    tree.setToken(token);
    return tree;
  }
}
