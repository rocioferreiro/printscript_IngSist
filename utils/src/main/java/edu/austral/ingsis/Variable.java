package edu.austral.ingsis;

public interface Variable {

  Variable EMPTY = new Variable() {
    @Override
    public String getName() {
      return null;
    }

    @Override
    public VariableType getType() {
      return new VariableType("invalid", -1);
    }

    @Override
    public String getValue() {
      return null;
    }

    @Override
    public void setName(String name) {}

    @Override
    public void setType(VariableType type) {}

    @Override
    public void setValue(String value) {}
  };

  String getName();

  VariableType getType();

  String getValue();

  void setName(String name);

  void setType(VariableType type);

  void setValue(String value);
}
