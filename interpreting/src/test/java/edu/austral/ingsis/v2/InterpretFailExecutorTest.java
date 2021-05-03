package edu.austral.ingsis.v2;

import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.austral.ingsis.ConcreteInterpreter;
import edu.austral.ingsis.InterpretationExecutionStrategy;
import edu.austral.ingsis.Interpreter;
import edu.austral.ingsis.InvalidCodeException;
import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InterpretFailExecutorTest {

  private final Interpreter interpreter =
      new ConcreteInterpreter(
          new InterpretationExecutionStrategy(),
          "PrintScript 1.0",
          Path.of("src/test/resources/rules.txt"));

  @ParameterizedTest
  @ValueSource(strings = {"interpret-invalid-param-if", "interpret-reassign-const"})
  public void testPrintStatement(String directory) {
    String testDirectory = "src/test/resources/1.1/" + directory + "/";
    File srcFile = new File(testDirectory + "main.ps");
    assertThrows(
        InvalidCodeException.class,
        () -> interpreter.interpret(srcFile, System.out::println, false));
  }
}
