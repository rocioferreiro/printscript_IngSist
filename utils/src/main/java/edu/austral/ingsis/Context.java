package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public class Context {

  private final List<Variable> variables;
  private Context subContextIf;
  private Context subContextElse;
  private ExecutingCommand nextExecute = ExecutingCommand.EMPTY;

  public Context() {
    this.variables = new ArrayList<>();
  }

  public Context(List<Variable> variables, Context subContextIf, Context subContextElse) {
    this.variables = variables;
    this.subContextIf = subContextIf;
    this.subContextElse = subContextElse;
  }

  public Context(Context subContextIf, Context subContextElse) {
    this.variables = new ArrayList<>();
    this.subContextIf = subContextIf;
    this.subContextElse = subContextElse;
  }

  public Context setContexts() {
    subContextIf = new Context();
    subContextElse = new Context();
    return this;
  }

  public List<Variable> getVariables() {
    return variables;
  }

  public List<Variable> getSubVariablesIf() {
    return subContextIf.getVariables();
  }

  public List<Variable> getSubVariablesElse() {
    return subContextElse.getVariables();
  }

  public void addVariable(Variable variable) {
    variables.add(variable);
  }

  public void addVariableToSubContextIf(Variable variable) {
    getSubVariablesIf().add(variable);
  }

  public void addVariableToSubContextElse(Variable variable) {
    getSubVariablesElse().add(variable);
  }

  public void updateVariable(Variable variable) {
    if(variables.isEmpty()) return;
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName()) && !variable.getValue().isEmpty()) {
        v.setValue(variable.getValue());
        return;
      }
    }
    subContextIf.updateVariable(variable);
    subContextElse.updateVariable(variable);
  }

  public boolean checkVariable(Variable variable) {
    if (variables.isEmpty()) return false;
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName())) return true;
    }
    return subContextIf.checkVariable(variable) || subContextElse.checkVariable(variable);
  }

  public VariableType getVariableType(String name) {
    if (variables.isEmpty()) return new VariableType();
    Variable find = getVariable(name);
    if (find.getName().equals(name)) {
      return find.getType();
    } else {
      return subContextIf.getVariableType(name);
    }
  }

  public boolean checkType(Variable variable) {
    if (variables.isEmpty()) return false;
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName()))
        return v.getType().getName().equals(variable.getType().getName());
    }
    return subContextIf.checkType(variable) || subContextElse.checkType(variable);
  }

  public void empty() {
    variables.clear();
    if (subContextIf != null) subContextIf.empty();
    if (subContextElse != null) subContextElse.empty();
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
      return find.getValue();}
    throw new InvalidCodeException("Variable out of Contaxt: " + name, new Position(0,0));
//    } else if (subContextIf.getVariable(name).getName().equals(name))
//      return subContextIf.getVariableValue(name);
//    return subContextElse.getVariableValue(name);
  }

  public Variable getVariable(String name) {
    for (Variable v : variables) {
      if (v.getName().equals(name)) return v;
    }
    return new ConcreteVariable();
  }

  public Context getSubContextIf() {
    return subContextIf;
  }

  public Context getSubContextElse() {
    return subContextElse;
  }
}
