package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public class Context {

  private final List<Variable> variables;
  private ExecutingCommand nextExecute = ExecutingCommand.EMPTY;

  public Context() {
    this.variables = new ArrayList<>();
  }

  public List<Variable> getVariables() {
    return variables;
  }

  public void addVariable(Variable variable) {
    variables.add(variable);
  }

  public void updateVariable(Variable variable) {
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName()) && !variable.getValue().isEmpty()) v.setValue(variable.getValue());
    }
  }

  public boolean checkVariable(Variable variable) {
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName())) return true;
    }
    return false;
  }

  public VariableType getVariableType(String name) {
    return variables.stream()
        .filter(v -> v.getName().equals(name))
        .findFirst()
        .orElse(Variable.EMPTY)
        .getType();
  }

  public boolean checkType(Variable variable) {
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName()))
        return v.getType().getName().equals(variable.getType().getName());
    }
    return false;
  }

  public void empty() {
    variables.clear();
  }

  public void setNextExecute(ExecutingCommand nextExecute) {
    this.nextExecute = nextExecute;
  }

  public void execute() {
    nextExecute.run();
    nextExecute = ExecutingCommand.EMPTY;
  }

  public String getVariableValue(String name) {
    return variables.stream()
            .filter(v -> v.getName().equals(name))
            .findFirst()
            .orElse(Variable.EMPTY)
            .getValue();
  }
}
