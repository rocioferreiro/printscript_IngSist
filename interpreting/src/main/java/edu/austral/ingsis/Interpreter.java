package edu.austral.ingsis;

import java.io.File;
import java.util.function.Consumer;

public interface Interpreter {
  void interpret(File file, Consumer<String> out, boolean progress);

  void interpret(String line, Consumer<String> out);

  void emptyContext();
}
