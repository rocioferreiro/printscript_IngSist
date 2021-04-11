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
    Variable left = new VariableBuilder().withName(ast.getLeftChild().getToken().getValue()).build();
    Variable right = operationCommand(ast.getRightChild(), context);
    if (!context.checkVariable(left))
      throw new InvalidCodeException(
          "Non declared variable!", ast.getLeftChild().getToken().getPosition());
    else {
      return new VariableBuilder().withName(left.getName()).withType(right.getType()).build();
    }
  }

  //TODO arreglar boolean ops
  public static Variable operationCommand(AST ast, Context context) {
    Token token = ast.getToken();
    if (token.getType().getCategory().equals(Operator.PLUS.getCategory())) { // CONTAINS OPERATOR
      Variable before = operationCommand(ast.getLeftChild(), context);
      Variable after = operationCommand(ast.getRightChild(), context);
      VariableType type =
          before.getType().getOrdinal() > after.getType().getOrdinal()
              ? before.getType()
              : after.getType();
      return new VariableBuilder().withType(type).build();
    } else {
      if (token.getType().getCategory().equals(KeyWord.STRING.getCategory())) { // VALUE
        return new VariableBuilder()
            .withType(token.getType().getName(), token.getType().getOrdinal())
            .build();
      } else { // VARIABLE
        Variable aux = new VariableBuilder().withName(token.getValue()).build();
        if (!context.checkVariable(aux))
          throw new InvalidCodeException("Non declared variable!", token.getPosition());
        return new VariableBuilder()
            .withName(aux.getName())
            .withType(context.getVariableType(aux.getName()))
            .build();
      }
    }
  }

  public static Variable printCommand(AST ast, Context context) {
    Token right = ast.getRightChild().getToken();
    if (right.getType().getCategory().equals(KeyWord.STRING.getCategory())) { // VALUE
      return new VariableBuilder().withType(right.getValue(), right.getType().getOrdinal()).build();
    } else { // VARIABLE
      Variable aux = new VariableBuilder().withName(right.getValue()).build();
      if (!context.checkVariable(aux))
        throw new InvalidCodeException("Non declared variable!", right.getPosition());
      return new VariableBuilder()
          .withName(aux.getName())
          .withType(context.getVariableType(aux.getName()))
          .build();
    }
  }
}
