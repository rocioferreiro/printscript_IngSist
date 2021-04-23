package edu.austral.ingsis.v1;

import edu.austral.ingsis.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterpretFailExecutorTest {
  private final Interpreter interpreter =
          new ConcreteInterpreter(new InterpretationExecutionStrategy(), "PrintScript 1.0", Path.of("src/test/resources/rules.txt"));

  @ParameterizedTest
  @ValueSource(strings = {"interpret-invalid"})
  public void testPrintStatement(String directory) {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    File srcFile = new File(testDirectory + "main.ps");
    assertThrows(InvalidCodeException.class, () -> interpreter.interpret(srcFile, System.out::println, false));
  }
}
