package edu.austral.ingsis;

import java.nio.file.Path;

public interface ExecutionStrategy {

  void execute(Lexer lexer, Parser parser, Executor executor, Path path);
  void execute(Lexer lexer, Parser parser, Executor executor, String line);
}
