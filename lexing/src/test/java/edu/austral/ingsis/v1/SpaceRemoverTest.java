package edu.austral.ingsis.v1;

import edu.austral.ingsis.PathReader;
import edu.austral.ingsis.StringSimplifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

import edu.austral.ingsis.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SpaceRemoverTest {

  @ParameterizedTest
  @ValueSource(strings = {"remove-spaces", "remove-spaces-in-spaces", "remove-spaces-with-final-string"})
  public void testPrintStatement(String directory) throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    Path srcPath = Path.of(testDirectory + "main.ps");
    List<String> expectedOutput = TestHelper.getLines(testDirectory + "output.txt");
    List<String> actualOutput = StringSimplifier.removeSpaces(PathReader.read(srcPath));
    Assertions.assertEquals(expectedOutput, actualOutput);
  }
}
