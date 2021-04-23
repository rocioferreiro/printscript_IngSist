package edu.austral.ingsis.v1;

import edu.austral.ingsis.ConcreteParser;
import edu.austral.ingsis.InvalidCodeException;
import edu.austral.ingsis.Parser;
import edu.austral.ingsis.ParserSerializer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserInvalidTest {
    private final Parser parser = new ConcreteParser(Path.of("src/test/resources/rules.txt"));

    @ParameterizedTest
    @ValueSource(strings = {"number-type-mismatch", "string-type-mismatch", "invalid-pattern"})
    public void testPrintStatement(String directory) throws FileNotFoundException {
        String testDirectory = "src/test/resources/1.0/" + directory + "/";
        File srcFile = new File(testDirectory + "in.csv");
        assertThrows(InvalidCodeException.class, () -> parser.parse(ParserSerializer.deserializeTokens(srcFile)));
    }
}
