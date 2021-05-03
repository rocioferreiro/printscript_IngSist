package edu.austral.ingsis.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.ConcreteLexer;
import edu.austral.ingsis.Lexer;
import edu.austral.ingsis.Serializer;
import edu.austral.ingsis.TestHelper;
import java.io.FileNotFoundException;
import java.util.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LexerLineTest {

  private final Lexer lexer = new ConcreteLexer();

  @ParameterizedTest
  @ValueSource(strings = {"happy-line"})
  public void testPrintStatement(String directory) throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    List<String> expectedOutput = TestHelper.getLines(testDirectory + "output.txt");
    String line = "let x: string = \"hola\";";
    List<String> actualOutput = Serializer.serializeTokens(lexer.scan(line));
    assertEquals(actualOutput, expectedOutput);
  }
}
