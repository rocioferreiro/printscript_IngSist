package edu.austral.ingsis;

public class VariableType {

  private final String name;
  private final int ordinal;

  public VariableType(String name, int ordinal) {
    this.name = name;
    this.ordinal = ordinal;
  }

  public VariableType() {
    name = "";
    ordinal = -1;
  }

  public String getName() {
    return name;
  }

  public int getOrdinal() {
    return ordinal;
  }

  public boolean equals(VariableType type) {
    return name.equals(type.name);
  }

  public boolean isEmpty() {
    return name.isEmpty();
  }
}
