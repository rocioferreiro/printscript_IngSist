package edu.austral.ingsis;

import java.nio.file.Path;

public interface Interpreter {
  void interpret(Path code);

  void interpret(String line);

  void emptyContext();
}
