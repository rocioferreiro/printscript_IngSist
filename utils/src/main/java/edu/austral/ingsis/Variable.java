package edu.austral.ingsis;

public interface Variable {

  String getName();

  VariableType getType();

  String getValue();

  void setName(String name);

  void setType(VariableType type);

  void setValue(String value);
}
