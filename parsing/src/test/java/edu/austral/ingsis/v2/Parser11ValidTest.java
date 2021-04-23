package edu.austral.ingsis.v2;

import edu.austral.ingsis.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Parser11ValidTest {

    private final Parser parser = new ConcreteParser(Path.of("src/test/resources/rules.txt"));

    @ParameterizedTest
    @ValueSource(strings = {"happy-if", "if-else", "if-inside-f"})
    public void testPrintStatement(String directory) throws FileNotFoundException {
        String testDirectory = "src/test/resources/1.1/" + directory + "/";
        File srcFile = new File(testDirectory + "in.csv");
        String expectedOutput = readLine(testDirectory + "out.txt");
        activateTokens();
        String actualOutput = ParserSerializer.serializeAst(parser.parse(ParserSerializer.deserializeTokens(srcFile)).getAst());
        assertEquals(expectedOutput, actualOutput);
    }

    private String readLine(String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file));
        return s.nextLine();
    }

    private void activateTokens() {
        TokenType[] types = {
                KeyWord.B_ASSIGNATION,
                KeyWord.C_DECLARATION,
                KeyWord.BOOLEAN,
                KeyWord.IF_STATEMENT,
                KeyWord.ELSE_STATEMENT,
                Operator.EQUAL_EQUAL,
                Operator.GREATER_EQUAL,
                Operator.MINOR_EQUAL,
                Operator.GREATER,
                Operator.MINOR,
                Operator.L_KEY,
                Operator.R_KEY
        };
        for (TokenType t : types) t.setAble(true);
    }
}
