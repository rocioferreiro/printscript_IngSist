package edu.austral.ingsis;


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

  public static Variable assignationCommand(AST tokens, Context context) {
    //    int index =
    //        IntStream.range(0, tokens.size())
    //            .filter(t -> tokens.get(t).getType().equals(Operator.EQUAL))
    //            .toArray()[0];
    //    Variable assigned = operationCommand(tokens.subList(index + 1, tokens.size()), context);
    //    if (tokens.get(declarationIndex).getType().equals(KeyWord.DECLARATION)) {
    //      return new VariableBuilder()
    //          .setName(tokens.get(declarationVariableIndex).getValue())
    //          .setType(assigned.getType())
    //          .build();
    //    } else {
    //      return new VariableBuilder()
    //          .setName(tokens.get(assignationVariableIndex).getValue())
    //          .setType(assigned.getType())
    //          .build();
    //    }
    return null;
  }

  public static Variable operationCommand(AST tokens, Context context) {
    //    if (tokens.stream()
    //        .anyMatch(t -> t.getType().getCategory().equals(Operator.PLUS.getCategory()))) { //
    // OPERATOR
    //      int index =
    //          IntStream.range(0, tokens.size())
    //              .filter(
    //                  t ->
    // tokens.get(t).getType().getCategory().equals(Operator.PLUS.getCategory()))
    //              .toArray()[0];
    //      List<Token> before = tokens.subList(0, index);
    //      List<Token> after = tokens.subList(index + 1, tokens.size());
    //      Variable beforeResult = operationCommand(before, context);
    //      Variable afterResult = operationCommand(after, context);
    //      VariableType type =
    //          beforeResult.getType().getOrdinal() < afterResult.getType().getOrdinal()
    //              ? beforeResult.getType()
    //              : afterResult.getType();
    //      return new VariableBuilder().setType(type).build();
    //    } else {
    //      if (tokens.get(0).getType().getCategory().equals(KeyWord.STRING.getCategory())) { //
    // VALUE
    //        return new VariableBuilder()
    //            .setType(tokens.get(0).getType().getName(), tokens.get(0).getType().getOrdinal())
    //            .build();
    //      } else {
    //        Variable aux =
    //            new
    // VariableBuilder().setName(tokens.get(assignationVariableIndex).getValue()).build();
    //        if (!context.checkVariable(aux))
    //          throw new RuntimeException(
    //              "Non declared variable! in: " + tokens.get(0).getPosition().toString());
    //        return new VariableBuilder()
    //            .setName(aux.getName())
    //            .setType(context.getVariableType(aux.getName()))
    //            .build();
    //      }
    //    }
    return null;
  }

  public static Variable printCommand(AST tokens, Context context) {
    //    if (tokens
    //        .get(methodVariableIndex)
    //        .getType()
    //        .getCategory()
    //        .equals(KeyWord.STRING.getCategory())) { // VALUE
    //      return new VariableBuilder()
    //          .setType(
    //              tokens.get(methodVariableIndex).getType().getName(),
    //              tokens.get(methodVariableIndex).getType().getOrdinal())
    //          .build();
    //    } else {
    //      Variable aux = new VariableBuilder().setName(tokens.get(2).getValue()).build();
    //      if (!context.checkVariable(aux))
    //        throw new RuntimeException(
    //            "Non declared variable! in: "
    //                + tokens.get(methodVariableIndex).getPosition().toString());
    //      return new VariableBuilder()
    //          .setName(aux.getName())
    //          .setType(context.getVariableType(aux.getName()))
    //          .build();
    //    }
    return null;
  }
}
