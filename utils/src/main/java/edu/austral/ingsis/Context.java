package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public class Context {

  private final List<Variable> variables;
  private Context subContext;
  private ExecutingCommand nextExecute = ExecutingCommand.EMPTY;

  public Context() {
    this.variables = new ArrayList<>();
    subContext = new Context();
  }

  public List<Variable> getVariables() {
    return variables;
  }

  public List<Variable> getSubVariables() {
    return subContext.getVariables();
  }

  public void addVariable(Variable variable) {
    variables.add(variable);
  }

  public void addVariableToSubContext(Variable variable) {
    getSubVariables().add(variable);
  }

  public void updateVariable(Variable variable) {
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName()) && !variable.getValue().isEmpty()) {
        v.setValue(variable.getValue());
        return;
      }
    }
    subContext.updateVariable(variable);
  }

  public boolean checkVariable(Variable variable) {
    if (variables.isEmpty()) return false;
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName())) return true;
    }
    return subContext.checkVariable(variable);
  }

  public VariableType getVariableType(String name) {
    if (variables.isEmpty()) return new VariableType();
    Variable find = getVariable(name);
    if (find.getName().equals(name)) {
      return find.getType();
    } else {
      return subContext.getVariableType(name);
    }
  }

  public boolean checkType(Variable variable) {
    if (variables.isEmpty()) return false;
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName()))
        return v.getType().getName().equals(variable.getType().getName());
    }
    return subContext.checkType(variable);
  }

  public void empty() {
    variables.clear();
    subContext.empty();
  }

  public void setNextExecute(ExecutingCommand nextExecute) {
    this.nextExecute = nextExecute;
  }

  public void execute() {
    nextExecute.run();
    nextExecute = ExecutingCommand.EMPTY;
  }

  public String getVariableValue(String name) {
    if (variables.isEmpty()) return "";
    Variable find = getVariable(name);
    if (find.getName().equals(name)) {
      return find.getValue();
    } else {
      return subContext.getVariableValue(name);
    }
  }

  public Variable getVariable(String name) {
    for (Variable v : variables) {
      if (v.getName().equals(name)) return v;
    }
    return new ConcreteVariable();
  }

  public Context getSubContext() {
    return subContext;
  }
}
