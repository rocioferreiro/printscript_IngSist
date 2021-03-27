package edu.austral.ingsis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathReader {

  public static String read(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new RuntimeException("Invalid path!");
    }
  }
}
