package edu.austral.ingsis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LexerTest {

    private final Lexer lexer;

    public LexerTest() {
        this.lexer = new ConcreteLexer();
    }

    @Test
    public void testScan() {
        Path path = Paths.get("testScan.txt");
        List<Token> actualResult = lexer.scan(path);

        List<Token> expectedResult = new ArrayList<>();
        expectedResult.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
        expectedResult.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
        expectedResult.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
        expectedResult.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "string", new Position(1, 7)));
        expectedResult.add(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 14)));
        expectedResult.add(new ConcreteToken(KeyWord.STRING, "\"hola\"", new Position(1, 16)));
        expectedResult.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 22)));

        compareTokens(actualResult, expectedResult);
    }

    private void compareTokens(List<Token> actual, List<Token> expected) {
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertEquals(actual.get(i).getType(), expected.get(i).getType());
            Assertions.assertEquals(actual.get(i).getValue(), expected.get(i).getValue());
            Assertions.assertEquals(actual.get(i).getPosition(), expected.get(i).getPosition());
        }
    }
}
