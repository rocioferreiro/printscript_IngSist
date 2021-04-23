package edu.austral.ingsis.v2;

import edu.austral.ingsis.ConcreteInterpreter;
import edu.austral.ingsis.Interpreter;
import edu.austral.ingsis.InvalidCodeException;
import edu.austral.ingsis.ValidationExecutionStrategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidateFailExecutorTest {

  private final Interpreter interpreter =
          new ConcreteInterpreter(new ValidationExecutionStrategy(), "PrintScript 1.1", Path.of("src/test/resources/rules.txt"));

  @ParameterizedTest
  @ValueSource(strings = {"validate-invalid-param-if", "validate-reassing-const"})
  public void testPrintStatement(String directory) {
    String testDirectory = "src/test/resources/1.1/" + directory + "/";
    File srcFile = new File(testDirectory + "main.ps");
    assertThrows(InvalidCodeException.class, () -> interpreter.interpret(srcFile, System.out::println, false));
  }
}
