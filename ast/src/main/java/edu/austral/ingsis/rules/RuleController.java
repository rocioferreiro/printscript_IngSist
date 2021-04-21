package edu.austral.ingsis.rules;

import edu.austral.ingsis.*;

public class RuleController {

  public static Variable declarationCommand(AST ast, Context context) {
    if (ast.getToken().getType().equals(Operator.T_ASSIGNATION)) {
      Token left = ast.getLeftChild().getToken();
      Token right = ast.getRightChild().getToken();
      return new VariableBuilder()
              .withName(left.getValue())
              .withType(right.getValue(), right.getType().getOrdinal())
              .build();
    }
    if (ast.getToken().getType().equals(Operator.EQUAL)) {
      return declarationCommand(ast.getLeftChild(), context);
    }
    return new VariableBuilder().build();
  }

  public static Variable assignationCommand(AST ast, Context context) {
    Variable left =
            new VariableBuilder().withName(ast.getLeftChild().getToken().getValue()).build();
    Variable right = operationCommand(ast.getRightChild(), context);
    if (!context.checkVariable(left))
      throw new InvalidCodeException(
              "Non declared variable!", ast.getLeftChild().getToken().getPosition());
    else {
      return new VariableBuilder().withName(left.getName()).withType(right.getType()).build();
    }
  }

  public static Variable operationCommand(AST ast, Context context) {
    Token token = ast.getToken();
    if (token.getType().getCategory().equals(Operator.EQUAL_EQUAL.getCategory()))
      return containsComparator(ast, context, token);
    if (token.getType().getCategory().equals(Operator.PLUS.getCategory()))
      return containsOperator(ast, context);
    if (token.getType().getCategory().equals(KeyWord.STRING.getCategory()))
      return containsValue(token);
    return containsVariable(context, token);
  }

  private static Variable containsComparator(AST ast, Context context, Token token) {
    Variable before = operationCommand(ast.getLeftChild(), context);
    Variable after = operationCommand(ast.getRightChild(), context);
    if (before.getType().getName().equals("string") || after.getType().getName().equals("string"))
      throw new InvalidCodeException("Can't compare strings", token.getPosition());
    VariableType type =
            new VariableType(KeyWord.B_ASSIGNATION.getRegex(), KeyWord.B_ASSIGNATION.getOrdinal());
    return new VariableBuilder().withType(type).build();
  }

  private static Variable containsOperator(AST ast, Context context) {
    Variable before = operationCommand(ast.getLeftChild(), context);
    Variable after = operationCommand(ast.getRightChild(), context);
    VariableType type =
            before.getType().getOrdinal() > after.getType().getOrdinal()
                    ? before.getType()
                    : after.getType();
    return new VariableBuilder().withType(type).build();
  }

  private static Variable containsValue(Token token) {
    return new VariableBuilder()
            .withType(token.getType().getName(), token.getType().getOrdinal())
            .build();
  }

  private static Variable containsVariable(Context context, Token token) {
    Variable aux = new VariableBuilder().withName(token.getValue()).build();
    if (!context.checkVariable(aux))
      throw new InvalidCodeException("Non declared variable!", token.getPosition());
    return new VariableBuilder()
            .withName(aux.getName())
            .withType(context.getVariableType(aux.getName()))
            .build();
  }

  public static Variable printCommand(AST ast, Context context) {
    Token right = ast.getRightChild().getToken();
    if (right.getType().getCategory().equals(KeyWord.STRING.getCategory())) { // VALUE
      return new VariableBuilder().withType(right.getValue(), right.getType().getOrdinal()).build();
    } else return containsVariable(context, right);
  }
}
