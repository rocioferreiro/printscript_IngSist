package edu.austral.ingsis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestHelper {

  public static String readLines(String file) throws FileNotFoundException {
    StringBuilder builder = new StringBuilder();
    Scanner s = new Scanner(new File(file));
    while (s.hasNextLine()) {
      builder.append(s.nextLine());
    }
    s.close();
    return builder.toString();
  }

  public static List<String> getLines(String file) throws FileNotFoundException {
    Scanner s = new Scanner(new File(file));
    ArrayList<String> list = new ArrayList<>();
    while (s.hasNextLine()) {
      list.add(s.nextLine());
    }
    s.close();
    return list;
  }

  public static String removeLast(String out) {
    String[] split = out.split("\n");
    StringBuilder result = new StringBuilder();
    for (String s : split) result.append(s);
    return result.toString();
  }
}
