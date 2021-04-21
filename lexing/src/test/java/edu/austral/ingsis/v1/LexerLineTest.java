package edu.austral.ingsis.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import edu.austral.ingsis.ConcreteLexer;
import edu.austral.ingsis.Lexer;
import edu.austral.ingsis.Serializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LexerLineTest {

  private final Lexer lexer = new ConcreteLexer();

  @SuppressWarnings("WeakerAccess")
  @Parameterized.Parameter()
  public String directory;

  @Parameterized.Parameters()
  public static List<Object> data() {
    return Arrays.asList(new Object[] {"happy-line"});
  }

  @Test
  public void testPrintStatement() throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    List<String> expectedOutput = readLines(testDirectory + "output.txt");
    String line = "let x: string = \"hola\";";
    List<String> actualOutput = Serializer.serializeTokens(lexer.scan(line));
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
