package edu.austral.ingsis.v2;

import edu.austral.ingsis.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateExecutorTest {

  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private static final PrintStream originalOut = System.out;
  private static final PrintStream originalErr = System.err;
  private final Interpreter interpreter =
          new ConcreteInterpreter(new ValidationExecutionStrategy(), "PrintScript 1.1", Path.of("src/test/resources/rules.txt"));

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
  @ValueSource(strings = {"validate-const-case", "validate-if-case"})
  public void testPrintStatement(String directory) throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.1/" + directory + "/";
    File srcFile = new File(testDirectory + "main.ps");
    String expectedOutput = TestHelper.readLines(testDirectory + "output.txt");
    interpreter.interpret(srcFile, System.out::println, false);
    assertEquals(expectedOutput, TestHelper.removeLast(outContent.toString()));
  }
}
