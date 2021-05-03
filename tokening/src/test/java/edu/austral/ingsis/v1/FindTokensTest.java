package edu.austral.ingsis.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.austral.ingsis.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FindTokensTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "find-keyword",
        "find-non-operators",
        "invalid-keyword",
        "invalid-keyword-has-operator"
      })
  public void testFindKeyWords(String directory) throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    File srcFile = new File(testDirectory + "in.txt");
    List<String> expectedOutput = TestHelper.getLines(testDirectory + "out.txt");
    List<Token> tokens =
        TokenSerializer.deserializeTokens(srcFile).stream()
            .map(KeyWord::findToken)
            .collect(Collectors.toList());
    assertThrows(InvalidCodeException.class, () -> TokenCleanUp.checkLastToken(tokens));
    List<String> actualOutput = TokenSerializer.serializeTokens(tokens);
    assertEquals(expectedOutput, actualOutput);
  }

  @ParameterizedTest
  @ValueSource(strings = {"find-operators"})
  public void testFindOperators(String directory) throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.0/" + directory + "/";
    File srcFile = new File(testDirectory + "in.txt");
    List<String> expectedOutput = TestHelper.getLines(testDirectory + "out.txt");
    List<Token> tokens =
        TokenSerializer.deserializeInput(srcFile).stream()
            .map(s -> Operator.findTokens(s, new Position(0, 0)))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    TokenCleanUp.checkLastToken(Collections.unmodifiableList(tokens));
    tokens = TokenCleanUp.checkLastTokenAndRemove(tokens);
    List<String> actualOutput = TokenSerializer.serializeTokens(tokens);
    assertEquals(expectedOutput, actualOutput);
  }
}
