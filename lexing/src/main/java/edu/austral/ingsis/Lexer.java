package edu.austral.ingsis;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface Lexer {
  List<Token> scan(File file);

  List<Token> scan(String line);
}
