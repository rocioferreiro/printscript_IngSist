package edu.austral.ingsis;

public class ConcreteVariable implements Variable {

  private String name;
  private VariableType type;
  private String value;

  public ConcreteVariable() {
    name = "";
    type = new VariableType();
    value = "";
  }

  public ConcreteVariable(String name, VariableType type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public VariableType getType() {
    return type;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setType(VariableType type) {
    this.type = type;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "(" + name + ", " + type.getName() + ")";
  }
}
