package edu.austral.ingsis;

public interface Variable {

  String getName();

  VariableType getType();

  void setName(String name);

  void setType(VariableType type);
}
