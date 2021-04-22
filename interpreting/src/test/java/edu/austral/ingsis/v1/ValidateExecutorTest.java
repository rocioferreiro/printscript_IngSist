package edu.austral.ingsis.v1;

import edu.austral.ingsis.ConcreteInterpreter;
import edu.austral.ingsis.Interpreter;
import edu.austral.ingsis.ValidationExecutionStrategy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class ValidateExecutorTest {

  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private static final PrintStream originalOut = System.out;
  private static final PrintStream originalErr = System.err;
  private final Interpreter interpreter =
          new ConcreteInterpreter(new ValidationExecutionStrategy(), "PrintScript 1.0");

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
    interpreter.emptyContext();
    outContent.reset();
  }

  @AfterAll
  public static void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @ParameterizedTest
  @ValueSource(strings = {"validate-happy-path"})
  public void testPrintStatement(String directory) throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    File srcFile = new File(testDirectory + "main.ps");
    String expectedOutput = readLines(testDirectory + "output.txt");
    interpreter.interpret(srcFile, System.out::println);
    assertEquals(outContent.toString(), expectedOutput);
  }

  private String readLines(String file) throws FileNotFoundException {
    StringBuilder builder = new StringBuilder();
    Scanner s = new Scanner(new File(file));
    while (s.hasNextLine()) {
      builder.append(s.nextLine());
    }
    s.close();
    return builder.toString();
  }
}
