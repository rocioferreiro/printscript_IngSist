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
    activateTokens();
    String testDirectory = "src/test/resources/1.1/" + directory + "/";
    File srcFile = new File(testDirectory + "main.ps");
    List<String> expectedOutput = readLines(testDirectory + "output.txt");
    List<String> actualOutput = Serializer.serializeTokens(lexer.scan(srcFile));
    assertEquals(actualOutput, expectedOutput);
  }

  private void activateTokens() {
    TokenType[] types = {
      KeyWord.B_ASSIGNATION,
      KeyWord.C_DECLARATION,
      KeyWord.BOOLEAN,
      KeyWord.IF_STATEMENT,
      KeyWord.ELSE_STATEMENT,
      Operator.EQUAL_EQUAL,
      Operator.GREATER_EQUAL,
      Operator.MINOR_EQUAL,
      Operator.GREATER,
      Operator.MINOR,
      Operator.L_KEY,
      Operator.R_KEY
    };
    for (TokenType t : types) t.setAble(true);
  }

  private List<String> readLines(String file) throws FileNotFoundException {
    Scanner s = new Scanner(new File(file));
    ArrayList<String> list = new ArrayList<>();
    while (s.hasNextLine()) {
      list.add(s.nextLine());
    }
    s.close();
    return list;
  }
}
