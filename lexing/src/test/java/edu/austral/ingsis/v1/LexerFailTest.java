package edu.austral.ingsis.v1;

import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.austral.ingsis.ConcreteLexer;
import edu.austral.ingsis.InvalidCodeException;
import edu.austral.ingsis.Lexer;
import java.io.File;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LexerFailTest {

  private final Lexer lexer = new ConcreteLexer();

  @ParameterizedTest
  @ValueSource(strings = {"unhappy-path", "unhappy-path-colons"})
  public void testPrintStatement(String directory) {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    File srcFile = new File(testDirectory + "main.ps");
    assertThrows(InvalidCodeException.class, () -> lexer.scan(srcFile));
  }
}
