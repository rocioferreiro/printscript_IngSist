package edu.austral.ingsis;

public class VariableBuilder {

  private final Variable variable;

  public VariableBuilder() {
    variable = new ConcreteVariable();
  }

  public Variable build() {
    return variable;
  }

  public VariableBuilder setName(String name) {
    variable.setName(name);
    return this;
  }

  public VariableBuilder setValue(String value) {
    variable.setValue(value);
    return this;
  }

  public VariableBuilder setType(String type, int ordinal) {
    variable.setType(new VariableType(type, ordinal));
    return this;
  }

  public VariableBuilder setType(VariableType type) {
    variable.setType(type);
    return this;
  }
}
