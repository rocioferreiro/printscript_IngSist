package edu.austral.ingsis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class PathReader {

  public static String read(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new RuntimeException("Invalid path!");
    }
  }

  public static String read(File file) {
    StringBuilder result = new StringBuilder();
    try {
      Scanner s = new Scanner(file);
      while (s.hasNextLine()) {
        result.append(s.nextLine());
      }
      return result.toString();
    } catch (IOException e) {
      throw new RuntimeException("Invalid file!");
    }
  }
}
