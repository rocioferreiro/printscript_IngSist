package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserTest {

  @Test
  public void testParse() {
    Parser parser = new ConcreteParser(getPath());

    List<Token> tokens = new ArrayList<>();
    tokens.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    tokens.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    tokens.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "string", new Position(1, 6)));
    tokens.add(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 12)));
    tokens.add(new ConcreteToken(KeyWord.STRING, "\"hola\"", new Position(1, 13)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 19)));
    tokens.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "y", new Position(1, 4)));
    tokens.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    tokens.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "number", new Position(1, 6)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 19)));

    Context expected = new Context();
    expected.addVariable(new ConcreteVariable("x", new VariableType("string", 0)));
    expected.addVariable(new ConcreteVariable("y", new VariableType("number", 1)));

    Context actual = parser.parse(tokens);
    compareContexts(actual, expected);
  }

  @Test
  public void testParseMissingLastSemicolon() {
    Parser parser = new ConcreteParser(getPath());

    List<Token> tokens = new ArrayList<>();
    tokens.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    tokens.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    tokens.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "string", new Position(1, 6)));
    tokens.add(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 12)));
    tokens.add(new ConcreteToken(KeyWord.STRING, "\"hola\"", new Position(1, 13)));

    assertThrows(
        InvalidCodeException.class,
        () -> parser.parse(tokens));
  }

  @Test
  public void testParseWithWrongPattern() {
    Parser parser = new ConcreteParser(getPath());

    List<Token> tokens = new ArrayList<>();
    tokens.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    tokens.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    tokens.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "string", new Position(1, 6)));
    tokens.add(new ConcreteToken(Operator.invalid, "^", new Position(1, 12)));
    tokens.add(new ConcreteToken(KeyWord.STRING, "\"hola\"", new Position(1, 13)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 19)));

    assertThrows(
        InvalidCodeException.class,
        () -> parser.parse(tokens));
  }

  @Test
  public void testParseWithTypeMismatch() {
    Parser parser = new ConcreteParser(getPath());

    List<Token> tokens = new ArrayList<>();
    tokens.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    tokens.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    tokens.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "string", new Position(1, 6)));
    tokens.add(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 12)));
    tokens.add(new ConcreteToken(KeyWord.STRING, "\"hola\"", new Position(1, 13)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 19)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    tokens.add(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 5)));
    tokens.add(new ConcreteToken(KeyWord.NUMBER, "12", new Position(1, 6)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 19)));

    assertThrows(
        InvalidCodeException.class,
        () -> parser.parse(tokens));
  }

  @Test
  public void testParseWithCreatedVariableAndTypeMismatch() {
    Parser parser = new ConcreteParser(getPath());

    List<Token> tokens = new ArrayList<>();
    tokens.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    tokens.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    tokens.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "string", new Position(1, 6)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 13)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(2, 1)));
    tokens.add(new ConcreteToken(Operator.EQUAL, "=", new Position(2, 2)));
    tokens.add(new ConcreteToken(KeyWord.NUMBER, "12", new Position(2, 3)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(2, 5)));

    assertThrows(
            InvalidCodeException.class,
            () -> parser.parse(tokens));
  }

  @Test
  public void testParseWithCreatedVariableAndTypeMatch() {
    Parser parser = new ConcreteParser(getPath());

    List<Token> tokens = new ArrayList<>();
    tokens.add(new ConcreteToken(KeyWord.DECLARATION, "let", new Position(1, 1)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 4)));
    tokens.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", new Position(1, 5)));
    tokens.add(new ConcreteToken(KeyWord.S_ASSIGNATION, "number", new Position(1, 6)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 13)));
    tokens.add(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(2, 1)));
    tokens.add(new ConcreteToken(Operator.EQUAL, "=", new Position(2, 2)));
    tokens.add(new ConcreteToken(KeyWord.NUMBER, "12", new Position(2, 3)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(2, 5)));

    Context expected = new Context();
    expected.addVariable(new ConcreteVariable("x", new VariableType("number", 0)));

    Context actual = parser.parse(tokens);
    compareContexts(actual, expected);
  }

  @Test
  public void testParseWithEmptyNamedVariable() {
    Parser parser = new ConcreteParser(getPath());

    List<Token> tokens = new ArrayList<>();
    tokens.add(new ConcreteToken(KeyWord.PRINTLN, "println", new Position(1, 1)));
    tokens.add(new ConcreteToken(Operator.L_PARENTHESIS, "(", new Position(1, 7)));
    tokens.add(new ConcreteToken(KeyWord.STRING, "\"hola\"", new Position(1, 8)));
    tokens.add(new ConcreteToken(Operator.R_PARENTHESIS, ")", new Position(1, 12)));
    tokens.add(new ConcreteToken(Operator.SEMICOLONS, ";", new Position(1, 13)));

    Context expected = new Context();
    expected.addVariable(new ConcreteVariable("", new VariableType("string", 0)));

    Context actual = parser.parse(tokens);
    compareContexts(actual, expected);

  }

  private Path getPath() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("rules.txt").getFile());
    return file.toPath();
  }

  private void compareContexts(Context actual, Context expected) {
    for (int i = 0; i < actual.getVariables().size(); i++) {
      Assertions.assertEquals(
          actual.getVariables().get(i).getType().getName(),
          expected.getVariables().get(i).getType().getName());
      Assertions.assertEquals(
          actual.getVariables().get(i).getName(), expected.getVariables().get(i).getName());
    }
  }
}
