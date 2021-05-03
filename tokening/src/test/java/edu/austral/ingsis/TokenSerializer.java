package edu.austral.ingsis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TokenSerializer {

  public static List<Token> deserializeTokens(File file) throws FileNotFoundException {
    Scanner scanner = new Scanner(file);
    List<Token> provisionales = new ArrayList<>();
    while (scanner.hasNext()) {
      provisionales.add(new ProvisionalToken(scanner.nextLine(), new Position(0, 0)));
    }
    return provisionales;
  }

  public static List<String> serializeTokens(List<Token> tokens) {
    return tokens.stream()
        .map(t -> "(" + t.getType().getName() + ", " + t.getValue() + ")")
        .collect(Collectors.toList());
  }

  public static List<String> deserializeInput(File file) throws FileNotFoundException {
    Scanner scanner = new Scanner(file);
    List<String> provisionales = new ArrayList<>();
    while (scanner.hasNext()) {
      provisionales.add(scanner.nextLine());
    }
    return provisionales;
  }
}
