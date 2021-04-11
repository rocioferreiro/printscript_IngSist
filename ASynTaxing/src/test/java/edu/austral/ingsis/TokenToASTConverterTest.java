package edu.austral.ingsis;

import edu.austral.ingsis.branches.AssignationAST;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenToASTConverterTest {

  @Test
  public void testTokenToASTConverter() {
    Position position = new Position(1, 2);
    Token token = new ConcreteToken(Operator.EQUAL, "=", position);
    AST actual = TokenToASTConverter.convert(token);
    AST expected = new AssignationAST();
    expected.setToken(token);
    Assertions.assertEquals(expected.getToken(), actual.getToken());
    Assertions.assertEquals(expected.getLeftChild().isEmpty(), actual.getLeftChild().isEmpty());
    Assertions.assertEquals(expected.getRightChild().isEmpty(), actual.getRightChild().isEmpty());
  }
}
