package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public class Context {

  private List<Variable> variables;

  public Context() {
    this.variables = new ArrayList<>();
  }

  public List<Variable> getVariables() {
    return variables;
  }

  public void addVariable(Variable variable) {
    variables.add(variable);
  }

  public boolean checkVariable(Variable variable) {
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName())) return true;
    }
    return false;
  }

  public VariableType getVariableType(String name) {
    return variables.stream().filter(v -> v.getName().equals(name)).findFirst().get().getType();
  }

  public boolean checkType(Variable variable) {
    for (Variable v : variables) {
      if (v.getName().equals(variable.getName()))
        return v.getType().getName().equals(variable.getType().getName());
    }
    return false;
  }

  public boolean compareTypes(Variable var1, Variable var2) {
    return var1.getType().equals(var2.getType());
  }
}
