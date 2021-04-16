package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenTest {

  @Test
  public void findTokenOfKeyWordWithVariable() {
    Position position = new Position(1, 4);
    Token expected = new ConcreteToken(KeyWord.VARIABLE_REF, "x", position);
    Token aux = new ProvisionalToken("x", position);
    Token actual = KeyWord.findToken(aux);
    Assertions.assertEquals(expected.getValue(), actual.getValue());
    Assertions.assertEquals(expected.getType(), actual.getType());
    Assertions.assertEquals(expected.getPosition().getRow(), actual.getPosition().getRow());

    Assertions.assertEquals(expected.getPosition().getColumn(), actual.getPosition().getColumn());
  }

  @Test
  public void findTokenOfKeyWordWithString1() {
    Position position = new Position(1, 4);
    Token expected = new ConcreteToken(KeyWord.STRING, "hola", position);
    Token aux = new ProvisionalToken("\"hola\"", position);
    Token actual = KeyWord.findToken(aux);
    Assertions.assertEquals(expected.getValue(), actual.getValue());
    Assertions.assertEquals(expected.getType(), actual.getType());
    Assertions.assertEquals(expected.getPosition().getRow(), actual.getPosition().getRow());
    Assertions.assertEquals(expected.getPosition().getColumn(), actual.getPosition().getColumn());
  }

  @Test
  public void findTokenOfKeyWordWithString2() {
    Position position = new Position(1, 4);
    Token expected = new ConcreteToken(KeyWord.STRING, "hola", position);
    Token aux = new ProvisionalToken("'hola'", position);
    Token actual = KeyWord.findToken(aux);
    Assertions.assertEquals(expected.getValue(), actual.getValue());
    Assertions.assertEquals(expected.getType(), actual.getType());
    Assertions.assertEquals(expected.getPosition().getRow(), actual.getPosition().getRow());
    Assertions.assertEquals(expected.getPosition().getColumn(), actual.getPosition().getColumn());
  }

  @Test
  public void findTokenOfKeyWordWithWrongRegex() {
    Position position = new Position(1, 4);
    Token expected = new ProvisionalToken("&&&", position);
    Token aux = new ProvisionalToken("&&&", position);
    Token actual = KeyWord.findToken(aux);
    Assertions.assertEquals(expected.getValue(), actual.getValue());
    Assertions.assertEquals(expected.getType(), actual.getType());
    Assertions.assertEquals(expected.getPosition().getRow(), actual.getPosition().getRow());
    Assertions.assertEquals(expected.getPosition().getColumn(), actual.getPosition().getColumn());
  }

  @Test
  public void findTokenOfOperator() {
    String s = "let x : number = 13;";
    Position position1 = new Position(1, 1);
    Position position3 = new Position(1, 7);
    Position position4 = new Position(1, 8);
    Position position5 = new Position(1, 16);
    Position position6 = new Position(1, 17);
    Position position7 = new Position(1, 20);
    List<Token> expected = new ArrayList<>();
    expected.add(new ProvisionalToken("let x ", position1));
    expected.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", position3));
    expected.add(new ProvisionalToken(" number ", position4));
    expected.add(new ConcreteToken(Operator.EQUAL, "=", position5));
    expected.add(new ProvisionalToken(" 13", position6));
    expected.add(new ConcreteToken(Operator.SEMICOLONS, ";", position7));
    List<Token> actual = Operator.findTokens(s, position1);
    for (int i = 0; i < actual.size(); i++) {
      Assertions.assertEquals(expected.get(i).getValue(), actual.get(i).getValue());
      Assertions.assertEquals(expected.get(i).getType(), actual.get(i).getType());
      Assertions.assertEquals(
          expected.get(i).getPosition().getRow(), actual.get(i).getPosition().getRow());
      Assertions.assertEquals(
          expected.get(i).getPosition().getColumn(), actual.get(i).getPosition().getColumn());
    }
  }

  @Test
  public void findTokenOfOperatorWithTwoOperatorsTogether() {
    String s = "let x := number ;";
    Position position1 = new Position(1, 1);
    Position position2 = new Position(1, 7);
    Position position3 = new Position(1, 8);
    Position position4 = new Position(1, 9);
    Position position5 = new Position(1, 17);
    List<Token> expected = new ArrayList<>();
    expected.add(new ProvisionalToken("let x ", position1));
    expected.add(new ConcreteToken(Operator.T_ASSIGNATION, ":", position2));
    expected.add(new ConcreteToken(Operator.EQUAL, "=", position3));
    expected.add(new ProvisionalToken(" number ", position4));
    expected.add(new ConcreteToken(Operator.SEMICOLONS, ";", position5));
    List<Token> actual = Operator.findTokens(s, position1);
    for (int i = 0; i < actual.size(); i++) {
      Assertions.assertEquals(expected.get(i).getValue(), actual.get(i).getValue());
      Assertions.assertEquals(expected.get(i).getType(), actual.get(i).getType());
      Assertions.assertEquals(
          expected.get(i).getPosition().getRow(), actual.get(i).getPosition().getRow());
      Assertions.assertEquals(
          expected.get(i).getPosition().getColumn(), actual.get(i).getPosition().getColumn());
    }
  }

  @Test
  public void findTokenOfOperatorWithoutOperators() {
    String s = "let x number";
    Position position1 = new Position(1, 1);
    List<Token> expected = new ArrayList<>();
    expected.add(new ProvisionalToken("let x number", position1));
    List<Token> actual = Operator.findTokens(s, position1);
    for (int i = 0; i < actual.size(); i++) {
      Assertions.assertEquals(expected.get(i).getValue(), actual.get(i).getValue());
      Assertions.assertEquals(expected.get(i).getType(), actual.get(i).getType());
      Assertions.assertEquals(
          expected.get(i).getPosition().getRow(), actual.get(i).getPosition().getRow());
      Assertions.assertEquals(
          expected.get(i).getPosition().getColumn(), actual.get(i).getPosition().getColumn());
    }
  }

  @Test
  public void findTokenOfOperatorEndingWithKeyToken() {
    String s = "let x = number";
    Position position1 = new Position(1, 1);
    Position position2 = new Position(1, 7);
    Position position3 = new Position(1, 8);
    List<Token> expected = new ArrayList<>();
    expected.add(new ProvisionalToken("let x ", position1));
    expected.add(new ConcreteToken(Operator.EQUAL, "=", position2));
    expected.add(new ProvisionalToken(" number", position3));
    List<Token> actual = Operator.findTokens(s, position1);
    for (int i = 0; i < actual.size(); i++) {
      Assertions.assertEquals(expected.get(i).getValue(), actual.get(i).getValue());
      Assertions.assertEquals(expected.get(i).getType(), actual.get(i).getType());
      Assertions.assertEquals(
          expected.get(i).getPosition().getRow(), actual.get(i).getPosition().getRow());
      Assertions.assertEquals(
          expected.get(i).getPosition().getColumn(), actual.get(i).getPosition().getColumn());
    }
  }

  @Test
  public void findTokenOfOperatorOfEmptyString() {
    String s = "";
    Position position1 = new Position(1, 1);
    List<Token> expected = new ArrayList<>();
    expected.add(new ProvisionalToken("", position1));
    List<Token> actual = Operator.findTokens(s, position1);
    for (int i = 0; i < actual.size(); i++) {
      Assertions.assertEquals(expected.get(i).getValue(), actual.get(i).getValue());
      Assertions.assertEquals(expected.get(i).getType(), actual.get(i).getType());
      Assertions.assertEquals(
          expected.get(i).getPosition().getRow(), actual.get(i).getPosition().getRow());
      Assertions.assertEquals(
          expected.get(i).getPosition().getColumn(), actual.get(i).getPosition().getColumn());
    }
  }
}
