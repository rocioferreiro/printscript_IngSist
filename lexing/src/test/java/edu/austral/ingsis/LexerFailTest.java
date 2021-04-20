package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LexerFailTest {

  private final Lexer lexer = new ConcreteLexer();

  @SuppressWarnings("WeakerAccess")
  @Parameterized.Parameter(value = 0)
  public String version;

  @SuppressWarnings("WeakerAccess")
  @Parameterized.Parameter(value = 1)
  public String directory;

  @Parameterized.Parameters(name = "version {0} - {1})")
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {"1.0", "unhappy-path"},
          //                            {"1.0", "no-enters"},
          //                            {"1.0", "number-case"},
          //                            {"1.0", "string-case"},
          //                            {"1.1", "boolean-case"}
        });
  }

  @Test
  public void testPrintStatement() {
    String testDirectory = "src/test/resources/" + version + "/" + directory + "/";
    Path srcPath = Path.of(testDirectory + "main.ps");
    assertThrows(InvalidCodeException.class, () -> lexer.scan(srcPath));
  }
}
