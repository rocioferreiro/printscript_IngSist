package edu.austral.ingsis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class LexerLineTest {

  private final Lexer lexer = new ConcreteLexer();

  @SuppressWarnings("WeakerAccess")
  @Parameterized.Parameter(value = 0)
  public String version;

  @SuppressWarnings("WeakerAccess")
  @Parameterized.Parameter(value = 1)
  public String directory;

  @Parameterized.Parameters(name = "version {0} - {1})")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {{"1.0", "happy-line"}});
  }

  @Test
  public void testPrintStatement() throws FileNotFoundException {
    String testDirectory = "src/test/resources/" + version + "/" + directory + "/";
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
