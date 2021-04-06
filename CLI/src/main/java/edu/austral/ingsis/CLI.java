package edu.austral.ingsis;

import java.nio.file.Paths;
import java.util.Scanner;

public class CLI {

  public static void run() {
    printExecuteOptions();
    Scanner scanner = new Scanner(System.in);
    Interpreter interpreter = new ConcreteInterpreter(Paths.get("rules.txt"), getStrategy(scanner));
    int readOption = 0;
    while (readOption != -1) {
      printReadOptions();
      readOption = scanner.nextInt();
      if (readOption == 1) readPath(interpreter);
      if (readOption == 2) readByLine(interpreter);
    }
  }

  private static ExecutionStrategy getStrategy(Scanner scanner) {
    ExecutionStrategy strategy = new InterpretationExecutionStrategy();
    int strategyOption = scanner.nextInt();
    if (strategyOption == 1) strategy = new ValidationExecutionStrategy();
    if (strategyOption == 2) strategy = new InterpretationExecutionStrategy();
    return strategy;
  }

  private static void printExecuteOptions() {
    System.out.println("Choose one of this options:");
    System.out.println("1 -> To validate");
    System.out.println("2 -> To execute");
  }

  private static void printReadOptions() {
    System.out.println("Choose one of this options:");
    System.out.println("1 -> To interpret a file");
    System.out.println("2 -> To open terminal");
  }

  private static void readPath(Interpreter interpreter) {
    interpreter.emptyContext();
    System.out.println("Enter valid path:");
    String line = "";
    Scanner scanner = new Scanner(System.in);
    while (!line.matches(".\\.txt")) {
      line = scanner.nextLine();
      if (!line.matches(".\\.txt")) {
        interpreter.interpret(Paths.get(line));
        break;
      }
    }
  }

  private static void readByLine(Interpreter interpreter) {
    interpreter.emptyContext();
    System.out.println("Start typing:");
    String line = "";
    Scanner scanner = new Scanner(System.in);

    while (!line.equals("exit")) {
      line = scanner.nextLine();
      if (!line.isEmpty() && !line.equals("exit")) {
        try {
          interpreter.interpret(line);
        } catch (InvalidCodeException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }
}
