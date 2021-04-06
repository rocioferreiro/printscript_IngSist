package edu.austral.ingsis;

import java.nio.file.Path;

public interface ExecutionStrategy {

  void execute(Lexer lexer, Parser parser, Executer executer, Path path);

  void execute(Lexer lexer, Parser parser, Executer executer, String line);
}
