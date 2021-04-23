package edu.austral.ingsis.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LexerPathTest {

  private final Lexer lexer = new ConcreteLexer();

  @ParameterizedTest
  @ValueSource(strings = {"boolean-case", "if-case"})
  public void testPrintStatement(String directory) throws FileNotFoundException {
    TokenCleanUp.activateTokens();
    String testDirectory = "src/test/resources/1.1/" + directory + "/";
    File srcFile = new File(testDirectory + "main.ps");
    List<String> expectedOutput = TestHelper.getLines(testDirectory + "output.txt");
    List<String> actualOutput = Serializer.serializeTokens(lexer.scan(srcFile));
    assertEquals(actualOutput, expectedOutput);
  }
}
