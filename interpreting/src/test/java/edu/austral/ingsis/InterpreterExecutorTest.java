package edu.austral.ingsis;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class InterpreterExecutorTest {

  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private static final PrintStream originalOut = System.out;
  private static final PrintStream originalErr = System.err;

  @BeforeAll
  public static void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterAll
  public static void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  public void testInterpreterInPath() {
    Interpreter interpreter = new ConcreteInterpreter(getPath("rules.txt"), new InterpretationExecutionStrategy());
    interpreter.interpret(getPath("testInterpreter.txt"));
    assertEquals("27.0", outContent.toString());
  }

  @Test
  public void testInterpreterInLine() {
    Interpreter interpreter = new ConcreteInterpreter(getPath("rules.txt"), new InterpretationExecutionStrategy());
    interpreter.interpret("let x:string = 'hola';");
    assertEquals("", outContent.toString());
  }

  private Path getPath(String s) {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(s).getFile());
    return file.toPath();
  }

  private void print(int amountOfLines, int actualLine) {
    String ANSI_RESET = "\u001B[0m";
    String ANSI_BLUE = "\033[0;34m";
    double percentage = ((double)actualLine)/amountOfLines;

    String string = "\t".repeat(15) + "Interpreting -> [" + "#".repeat(actualLine) +
            " ".repeat(amountOfLines - actualLine) +
            "] " + (int) (percentage*100) + "%";
    System.out.println(ANSI_BLUE + string + ANSI_RESET);
  }
}
