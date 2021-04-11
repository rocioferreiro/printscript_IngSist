package edu.austral.ingsis;

public class ConcreteVariable implements Variable {

  private String name;
  private VariableType type;
  private String value;
  private boolean isConst;

  public ConcreteVariable() {
    name = "";
    type = new VariableType();
    value = "";
    isConst = false;
  }

  public ConcreteVariable(String name, VariableType type) {
    this.name = name;
    this.type = type;
    isConst = false;
  }

  public ConcreteVariable(String name, VariableType type, boolean isConst) {
    this.name = name;
    this.type = type;
    this.isConst = isConst;
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
  public String getValue() {
    return value;
  }

  @Override
  public boolean isConst() {
    return isConst;
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
  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public void setIsConst(boolean isConst) {
    this.isConst = isConst;
  }

  @Override
  public String toString() {
    return "(" + name + ", " + type.getName() + ")";
  }
}
