package edu.austral.ingsis.v1;

import edu.austral.ingsis.ConcreteParser;
import edu.austral.ingsis.Parser;
import edu.austral.ingsis.ParserSerializer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserValidTest {

    private final Parser parser = new ConcreteParser(Path.of("src/test/resources/rules.txt"));

    @ParameterizedTest
    @ValueSource(strings = {"happy-string", "happy-number", "empty-named-variable"})
    public void testPrintStatement(String directory) throws FileNotFoundException {
        String testDirectory = "src/test/resources/1.0/" + directory + "/";
        File srcFile = new File(testDirectory + "in.csv");
        String expectedOutput = readLine(testDirectory + "out.txt");
        String actualOutput = ParserSerializer.serializeAst(parser.parse(ParserSerializer.deserializeTokens(srcFile)).getAst());
        assertEquals(expectedOutput, actualOutput);
    }

    private String readLine(String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file));
        return s.nextLine();
    }
}