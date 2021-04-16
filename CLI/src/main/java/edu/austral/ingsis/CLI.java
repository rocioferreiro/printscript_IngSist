package edu.austral.ingsis;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI {

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_YELLOW = "\033[0;33m";
  private static final String ANSI_RED_BOLD = "\033[1;31m";
  private static final List<Version> versions = new ArrayList<>();

  public static void run() {
    setVersions();
    printExecuteOptions();
    Scanner scanner = new Scanner(System.in);
    ExecutionStrategy strategy = getStrategy(scanner);
    printVersionOptions();
    Version version = getVersion(scanner);
    Interpreter interpreter = new ConcreteInterpreter(Paths.get("rules.txt"), strategy, version);
    int readOption = 0;
    while (readOption != -1) {
      printReadOptions();
      readOption = scanner.nextInt();
      if (readOption == 1) readPath(interpreter);
      if (readOption == 2) readByLine(interpreter);
    }
  }

  private static void setVersions() {
    versions.add(new Version("PrintScript 1.0", new ArrayList<>()));
    TokenType[] typesPrint1 = {
      KeyWord.B_ASSIGNATION,
      KeyWord.C_DECLARATION,
      KeyWord.BOOLEAN,
      KeyWord.IF_STATEMENT,
      KeyWord.ELSE_STATEMENT,
      Operator.EQUAL_EQUAL,
      Operator.GREATER_EQUAL,
      Operator.MINOR_EQUAL,
      Operator.GREATER,
      Operator.MINOR,
      Operator.L_KEY,
      Operator.R_KEY
    };
    versions.add(new Version("PrintScript 1.1", Arrays.asList(typesPrint1)));
  }

  private static Version getVersion(Scanner scanner) {
    Version version = new Version();
    int versionOption = scanner.nextInt();
    if (versionOption == 1) version = versions.get(0);
    if (versionOption == 2) version = versions.get(1);
    return version;
  }

  private static ExecutionStrategy getStrategy(Scanner scanner) {
    ExecutionStrategy strategy = new InterpretationExecutionStrategy();
    int strategyOption = scanner.nextInt();
    if (strategyOption == 1) strategy = new ValidationExecutionStrategy();
    if (strategyOption == 2) strategy = new InterpretationExecutionStrategy();
    return strategy;
  }

  private static void printVersionOptions() {
    System.out.println(ANSI_YELLOW + "Choose one of this options:" + ANSI_RESET);
    System.out.println(ANSI_YELLOW + "1 -> PrintScript 1.0" + ANSI_RESET);
    System.out.println(ANSI_YELLOW + "2 -> PrintScript 1.1\n" + ANSI_RESET);
  }

  private static void printExecuteOptions() {
    System.out.println(ANSI_YELLOW + "Choose one of this options:" + ANSI_RESET);
    System.out.println(ANSI_YELLOW + "1 -> To validate" + ANSI_RESET);
    System.out.println(ANSI_YELLOW + "2 -> To execute\n" + ANSI_RESET);
  }

  private static void printReadOptions() {
    String ANSI_RESET = "\u001B[0m";
    String ANSI_YELLOW = "\033[0;33m";
    System.out.println(ANSI_YELLOW + "Choose one of this options:" + ANSI_RESET);
    System.out.println(ANSI_YELLOW + "1 -> To interpret a file" + ANSI_RESET);
    System.out.println(ANSI_YELLOW + "2 -> To open terminal\n" + ANSI_RESET);
  }

  private static void readPath(Interpreter interpreter) {
    interpreter.emptyContext();
    System.out.println(ANSI_YELLOW + "Enter valid path:" + ANSI_RESET);
    String line = "";
    Scanner scanner = new Scanner(System.in);
    while (!line.matches(".\\.txt")) {
      line = scanner.nextLine();
      if (!line.matches(".\\.txt")) {
        try {
          interpreter.interpret(Paths.get(line));
          break;
        } catch (InvalidCodeException e) {
          System.out.println(ANSI_RED_BOLD + e.getMessage() + ANSI_RESET);
        }
      }
    }
  }

  private static void readByLine(Interpreter interpreter) {
    interpreter.emptyContext();
    System.out.println(ANSI_YELLOW + "Start typing:" + ANSI_RESET);
    String line = "";
    Scanner scanner = new Scanner(System.in);

    while (!line.equals("exit")) {
      System.out.print("~ ");
      line = scanner.nextLine();
      if (!line.isEmpty() && !line.equals("exit")) {
        try {
          interpreter.interpret(line);
        } catch (InvalidCodeException e) {
          System.out.println(ANSI_RED_BOLD + e.getMessage() + ANSI_RESET);
        }
      }
    }
  }
}
