package edu.austral.ingsis;

import edu.austral.ingsis.branches.AssignationAST;
import edu.austral.ingsis.branches.DeclarationAST;
import edu.austral.ingsis.branches.MethodAST;
import edu.austral.ingsis.branches.OperationAST;
import edu.austral.ingsis.leaves.*;

import java.util.HashMap;
import java.util.Map;

public class TokenToASTConverter {

  private static Map<TokenType, ASTGeneratorCommand> table =
      new HashMap<>() {
        {
          put(Operator.PLUS, OperationAST::new);
          put(Operator.EQUAL, AssignationAST::new);
          put(Operator.ASTERISK, OperationAST::new);
          put(Operator.DASH, OperationAST::new);
          put(Operator.HYPHEN, OperationAST::new);
          put(Operator.L_PARENTHESIS, EmptyAST::new);
          put(Operator.R_PARENTHESIS, EmptyAST::new);
          put(Operator.T_ASSIGNATION, DeclarationAST::new);
          put(Operator.SEMICOLONS, EmptyAST::new);
          put(KeyWord.DECLARATION, EmptyAST::new);
          put(KeyWord.STRING, LiteralAST::new);
          put(KeyWord.NUMBER, LiteralAST::new);
          put(KeyWord.N_ASSIGNATION, NumberAssignationAST::new);
          put(KeyWord.S_ASSIGNATION, StringAssignationAST::new);
          put(KeyWord.VARIABLE_REF, VariableAST::new);
          put(KeyWord.PRINTLN, MethodAST::new);
        }
      };

  public static AST convert(Token token) {
    AST tree = table.get(token.getType()).create();
    tree.setToken(token);
    return tree;
  }
}
