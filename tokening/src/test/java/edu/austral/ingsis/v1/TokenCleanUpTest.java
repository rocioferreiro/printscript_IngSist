package edu.austral.ingsis.v1;

import edu.austral.ingsis.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenCleanUpTest {

    @ParameterizedTest
    @ValueSource(strings = {"find-operators"})
    public void testFindOperators(String directory) throws FileNotFoundException {
        String testDirectory = "src/test/resources/1.0/" + directory + "/";
        File srcFile = new File(testDirectory + "in.txt");
        String expectedOutput = TestHelper.readLines(testDirectory + "clean-out.txt");
        List<Token> tokens = TokenSerializer.deserializeInput(srcFile).stream()
                .map(s -> Operator.findTokens(s, new Position(0,0)))
                .flatMap(Collection::stream).collect(Collectors.toList());
        String actualOutput = ""+TokenCleanUp.getIndexOfNextSeparator(tokens);
        assertEquals(expectedOutput, actualOutput);
    }
}
