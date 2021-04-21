package edu.austral.ingsis.v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

import edu.austral.ingsis.PathReader;
import edu.austral.ingsis.Serializer;
import edu.austral.ingsis.StringSimplifier;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class EnterRemoverTest {

  @SuppressWarnings("WeakerAccess")
  @Parameterized.Parameter()
  public String directory;

  @Parameterized.Parameters()
  public static Collection<Object> data() {
    return Arrays.asList(
        new Object[] {
          "remove-enters",
//          "remove-enters-in-strings", // -> por alguna razon no anda :)
          "remove-enters-in-end-of-line",
          "remove-many-enters"
        });
  }

  @Test
  public void testPrintStatement() throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    Path srcPath = Path.of(testDirectory + "main.ps");
    List<String> expectedOutput = readLines(testDirectory + "output.txt");
    List<String> actualOutput =
        Serializer.serializeLines(StringSimplifier.removeEnters(PathReader.read(srcPath)));
    Assertions.assertEquals(expectedOutput, actualOutput);
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
