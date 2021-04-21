package edu.austral.ingsis.v1;

import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.austral.ingsis.ConcreteLexer;
import edu.austral.ingsis.InvalidCodeException;
import edu.austral.ingsis.Lexer;
import java.nio.file.Path;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LexerFailTest {

  private final Lexer lexer = new ConcreteLexer();

  @SuppressWarnings("WeakerAccess")
  @Parameterized.Parameter()
  public String directory;

  @Parameterized.Parameters()
  public static List<Object> data() {
    return Arrays.asList(new Object[] {"unhappy-path", "unhappy-path-colons"});
  }

  @Test
  public void testPrintStatement() {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    Path srcPath = Path.of(testDirectory + "main.ps");
    assertThrows(InvalidCodeException.class, () -> lexer.scan(srcPath));
  }
}
