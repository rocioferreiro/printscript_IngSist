package edu.austral.ingsis;

import java.io.File;
import java.util.function.Consumer;

public interface Interpreter {
  void interpret(File file, Consumer<String> out);

  void interpret(String line, Consumer<String> out);

  void emptyContext();
}
