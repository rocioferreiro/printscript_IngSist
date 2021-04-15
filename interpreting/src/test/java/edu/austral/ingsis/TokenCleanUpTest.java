package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;

public class TokenCleanUpTest {

  @Test
  public void testCheckIndexOfNextSemiColumnFailure() {
    List<Token> tokens = new ArrayList<>();
    Assertions.assertEquals(-1, TokenCleanUp.getIndexOfNextSeparator(tokens));
  }
}
