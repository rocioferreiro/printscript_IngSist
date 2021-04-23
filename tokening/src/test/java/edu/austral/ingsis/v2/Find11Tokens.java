package edu.austral.ingsis.v2;

import edu.austral.ingsis.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Find11Tokens {
    @ParameterizedTest
    @ValueSource(strings = {"find-keyword", "find-non-operators"})
    public void testFindKeyWords(String directory) throws FileNotFoundException {
        String testDirectory = "src/test/resources/1.1/" + directory + "/";
        File srcFile = new File(testDirectory + "in.txt");
        TokenCleanUp.activateTokens();
        List<String> expectedOutput = TestHelper.getLines(testDirectory + "out.txt");
        List<String> actualOutput = TokenSerializer.serializeTokens(
                TokenSerializer.deserializeTokens(srcFile).stream()
                        .map(KeyWord::findToken).collect(Collectors.toList()));
        assertEquals(expectedOutput, actualOutput);
    }
}
