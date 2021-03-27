package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.List;

public interface Lexer {
  List<Token> scan(Path path);
}
