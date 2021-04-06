package edu.austral.ingsis.rules;

import edu.austral.ingsis.*;

public class RuleController {

  public static Variable declarationCommand(AST ast, Context context) {
    if (ast.getToken().getType().equals(Operator.T_ASSIGNATION)) {
      Token left = ast.getLeftChild().getToken();
      Token right = ast.getRightChild().getToken();
      return new VariableBuilder()
          .setName(left.getValue())
          .setType(right.getValue(), right.getType().getOrdinal())
          .build();
    }
    if (ast.getToken().getType().equals(Operator.EQUAL)) {
      Variable declared = declarationCommand(ast.getLeftChild(), context);
      Variable assigned = operationCommand(ast.getRightChild(), context);
      if (declared.getType().equals(assigned.getType())) return declared;
      throw new InvalidCodeException("Type mismatch!", ast.getToken().getPosition());
    }
    return new VariableBuilder().build();
  }

  public static Variable assignationCommand(AST ast, Context context) {
    Variable left = new VariableBuilder().setName(ast.getLeftChild().getToken().getValue()).build();
    Variable right = operationCommand(ast.getRightChild(), context);
    if (!context.checkVariable(left))
      throw new InvalidCodeException(
          "Non declared variable!", ast.getLeftChild().getToken().getPosition());
    else {
      if (right.getType().equals(context.getVariableType(left.getName())))
        return new VariableBuilder().setName(left.getName()).setType(right.getType()).build();
      else throw new InvalidCodeException("Type mismatch", ast.getToken().getPosition());
    }
  }

  public static Variable operationCommand(AST ast, Context context) {
    Token token = ast.getToken();
    if (token.getType().getCategory().equals(Operator.PLUS.getCategory())) { // CONTAINS OPERATOR
      Variable before = operationCommand(ast.getLeftChild(), context);
      Variable after = operationCommand(ast.getRightChild(), context);
      VariableType type =
          before.getType().getOrdinal() < after.getType().getOrdinal()
              ? before.getType()
              : after.getType();
      return new VariableBuilder().setType(type).build();
    } else {
      if (token.getType().getCategory().equals(KeyWord.STRING.getCategory())) { // VALUE
        return new VariableBuilder()
            .setType(token.getType().getName(), token.getType().getOrdinal())
            .build();
      } else { // VARIABLE
        Variable aux = new VariableBuilder().setName(token.getValue()).build();
        if (!context.checkVariable(aux))
          throw new InvalidCodeException("Non declared variable!", token.getPosition());
        return new VariableBuilder()
            .setName(aux.getName())
            .setType(context.getVariableType(aux.getName()))
            .build();
      }
    }
  }

  public static Variable printCommand(AST ast, Context context) {
    Token right = ast.getRightChild().getToken();
    if (right.getType().getCategory().equals(KeyWord.STRING.getCategory())) { // VALUE
      return new VariableBuilder().setType(right.getValue(), right.getType().getOrdinal()).build();
    } else { // VARIABLE
      Variable aux = new VariableBuilder().setName(right.getValue()).build();
      if (!context.checkVariable(aux))
        throw new InvalidCodeException("Non declared variable!", right.getPosition());
      return new VariableBuilder()
          .setName(aux.getName())
          .setType(context.getVariableType(aux.getName()))
          .build();
    }
  }
}
