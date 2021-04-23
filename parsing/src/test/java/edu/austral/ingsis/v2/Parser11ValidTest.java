package edu.austral.ingsis.v2;

import edu.austral.ingsis.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Parser11ValidTest {

    private final Parser parser = new ConcreteParser(Path.of("src/test/resources/rules.txt"));

    @ParameterizedTest
    @ValueSource(strings = {"happy-if", "if-else", "if-inside-f"})
    public void testPrintStatement(String directory) throws FileNotFoundException {
        String testDirectory = "src/test/resources/1.1/" + directory + "/";
        File srcFile = new File(testDirectory + "in.csv");
        String expectedOutput = TestHelper.readLines(testDirectory + "out.txt");
        TokenCleanUp.activateTokens();
        String actualOutput = ParserSerializer.serializeAst(parser.parse(ParserSerializer.deserializeTokens(srcFile)).getAst());
        assertEquals(expectedOutput, actualOutput);
    }
}
