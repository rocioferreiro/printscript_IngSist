package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

public class LexerTest {

  private final Lexer lexer;

  @Rule public ExpectedException thrown = ExpectedException.none();

  public LexerTest() {
    this.lexer = new ConcreteLexer();
  }

  @Test
  public void testScanWithPath() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(Objects.requireNonNull(classLoader.getResource("testScan.txt")).getFile());
    Path path = file.toPath();
    List<Token> actualResult = lexer.scan(path);

    List<Token> expectedResult = new ArrayList<>();
    expectedResult.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    expectedResult.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    expectedResult.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    expectedResult.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "string", new Position(1, 6)));
    expectedResult.add(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 12)));
    expectedResult.add(new ConcreteToken(KeyWord.STRING, "hola", new Position(1, 13)));
    expectedResult.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 19)));

    compareTokens(actualResult, expectedResult);
  }

  @Test
  public void testScanWithPathWithoutEnters() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file =
        new File(
            Objects.requireNonNull(classLoader.getResource("testScanWithEnters.txt")).getFile());
    Path path = file.toPath();
    List<Token> actualResult = lexer.scan(path);

    List<Token> expectedResult = new ArrayList<>();
    expectedResult.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    expectedResult.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    expectedResult.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    expectedResult.add(new ConcreteToken(KeyWord.N_ASSIGNATION, "number", new Position(1, 6)));
    expectedResult.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 12)));

    expectedResult.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(3, 1)));
    expectedResult.add(new ConcreteToken(Operator.EQUAL, "=", new Position(3, 2)));
    expectedResult.add(new ConcreteToken(KeyWord.NUMBER, "12", new Position(3, 3)));
    expectedResult.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(3, 5)));

    compareTokens(actualResult, expectedResult);
  }

  @Test
  public void testScanWithLine() {
    List<Token> actualResult = lexer.scan("let x: string = \"hola\";");

    List<Token> expectedResult = new ArrayList<>();
    expectedResult.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    expectedResult.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    expectedResult.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    expectedResult.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "string", new Position(1, 6)));
    expectedResult.add(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 12)));
    expectedResult.add(new ConcreteToken(KeyWord.STRING, "hola", new Position(1, 13)));
    expectedResult.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 19)));

    compareTokens(actualResult, expectedResult);
  }

  @Test
  public void testScanWithLineWithoutColons() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file =
        new File(
            Objects.requireNonNull(classLoader.getResource("testScanWithoutColons.txt")).getFile());
    Path path = file.toPath();
    List<Token> actualResult = lexer.scan(path);

    List<Token> expectedResult = new ArrayList<>();
    expectedResult.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    expectedResult.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    expectedResult.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    expectedResult.add(new ConcreteToken(KeyWord.N_ASSIGNATION, "number", new Position(1, 6)));
    expectedResult.add(new ConcreteToken(Operator.EQUAL, "=", new Position(2, 1)));
    expectedResult.add(new ConcreteToken(KeyWord.NUMBER, "12", new Position(2, 2)));
    expectedResult.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(2, 4)));

    compareTokens(actualResult, expectedResult);
  }

  @Test
  public void testScanAndFail() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("testScanAndFail.txt").getFile());
    Path path = file.toPath();
    assertThrows(InvalidCodeException.class, () -> lexer.scan(path));
  }

  private void compareTokens(List<Token> actual, List<Token> expected) {
    for (int i = 0; i < actual.size(); i++) {
      Assertions.assertEquals(actual.get(i).getType(), expected.get(i).getType());
      Assertions.assertEquals(actual.get(i).getValue(), expected.get(i).getValue());
      Assertions.assertEquals(
          actual.get(i).getPosition().getColumn(), expected.get(i).getPosition().getColumn());
      Assertions.assertEquals(
          actual.get(i).getPosition().getRow(), expected.get(i).getPosition().getRow());
    }
  }
}
