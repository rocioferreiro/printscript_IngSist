package edu.austral.ingsis;

public class VariableBuilder {

  private final Variable variable;

  public VariableBuilder() {
    variable = new ConcreteVariable();
  }

  public Variable build() {
    return variable;
  }

  public VariableBuilder withName(String name) {
    variable.setName(name);
    return this;
  }

  public VariableBuilder withValue(String value) {
    variable.setValue(value);
    return this;
  }

  public VariableBuilder withType(String type, int ordinal) {
    variable.setType(new VariableType(type, ordinal));
    return this;
  }

  public VariableBuilder withType(VariableType type) {
    variable.setType(type);
    return this;
  }

  public VariableBuilder asConst(boolean bool) {
    variable.setIsConst(bool);
    return this;
  }
}
