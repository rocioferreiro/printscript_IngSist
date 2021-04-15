package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public class SubContext {
  private final List<Variable> variables;
  private ExecutingCommand nextExecute = ExecutingCommand.EMPTY;

  public SubContext() {
    variables = new ArrayList<>();
  }
}
