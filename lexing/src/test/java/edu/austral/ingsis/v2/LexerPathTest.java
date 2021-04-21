package edu.austral.ingsis.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LexerPathTest {

  private final Lexer lexer = new ConcreteLexer();

  @SuppressWarnings("WeakerAccess")
  @Parameterized.Parameter()
  public String directory;

  @Parameterized.Parameters()
  public static Collection<Object> data() {
    return Arrays.asList(new Object[] {"boolean-case", "if-case"});
  }

  @Test
  public void testPrintStatement() throws FileNotFoundException {
    activateTokens();
    String testDirectory = "src/test/resources/1.1/" + directory + "/";
    Path srcPath = Path.of(testDirectory + "main.ps");
    List<String> expectedOutput = readLines(testDirectory + "output.txt");
    List<String> actualOutput = Serializer.serializeTokens(lexer.scan(srcPath));
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
