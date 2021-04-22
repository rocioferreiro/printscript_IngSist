package edu.austral.ingsis.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.ConcreteLexer;
import edu.austral.ingsis.Lexer;
import edu.austral.ingsis.Serializer;
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
    return Arrays.asList(new Object[] {"happy-path", "no-enters", "number-case", "string-case"});
  }

  @Test
  public void testPrintStatement() throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    File srcFile = new File(testDirectory + "main.ps");
    List<String> expectedOutput = readLines(testDirectory + "output.txt");
    List<String> actualOutput = Serializer.serializeTokens(lexer.scan(srcFile));
    assertEquals(actualOutput, expectedOutput);
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
