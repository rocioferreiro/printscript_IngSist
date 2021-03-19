package edu.austral.ingsis;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface Lexer {
    List<Sentence> scan(Path path);
}
