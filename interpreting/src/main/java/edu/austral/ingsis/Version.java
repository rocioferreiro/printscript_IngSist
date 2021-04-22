package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public class Version {

  private final String name;
  private final List<TokenType> toAccept;

  public Version() {
    name = "";
    toAccept = new ArrayList<>();
  }

  public Version(String name, List<TokenType> toAccept) {
    this.name = name;
    this.toAccept = toAccept;
  }

  public String getName() {
    return name;
  }

  public List<TokenType> getToAccept() {
    return toAccept;
  }
}
