package edu.austral.ingsis.rules;

import edu.austral.ingsis.AST;
import edu.austral.ingsis.Command;
import edu.austral.ingsis.Context;

public enum RuleType {
  DECLARATION(1, RuleController::declarationCommand),
  ASSIGNATION(2, RuleController::assignationCommand),
  PRINT(3, RuleController::printCommand),
  CONST(4, RuleController::declarationCommand),
  INVALID(0, (AST ast, Context context) -> null);

  private final int id;
  private final Command command;

  RuleType(int id, Command command) {
    this.id = id;
    this.command = command;
  }

  public static RuleType ruleOfId(int id) {
    for (RuleType type : values()) {
      if (type.id == id) return type;
    }
    return INVALID;
  }

  public Command getCommand() {
    return command;
  }
}
