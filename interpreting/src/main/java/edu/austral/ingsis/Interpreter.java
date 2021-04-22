package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.function.Consumer;

public interface Interpreter {
  void interpret(Path code, Consumer<String> out);

  void interpret(String line, Consumer<String> out);

  void emptyContext();
}
