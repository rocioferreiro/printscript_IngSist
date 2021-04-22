package edu.austral.ingsis;

public interface Command {
  Variable execute(AST tokens, Context context);
}
