package edu.austral.ingsis.v1;

import edu.austral.ingsis.PathReader;
import edu.austral.ingsis.Serializer;
import edu.austral.ingsis.StringSimplifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class EnterRemoverTest {

  @ParameterizedTest
  @ValueSource(strings = {"remove-enters", "remove-enters-in-end-of-line", "remove-many-enters"})
  public void testPrintStatement(String directory) throws FileNotFoundException {
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
