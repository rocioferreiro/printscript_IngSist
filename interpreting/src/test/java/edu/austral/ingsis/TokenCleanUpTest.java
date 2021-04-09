package edu.austral.ingsis;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class TokenCleanUpTest {

    @Test
    public void testCheckIndexOfNextSemiColumnFailure(){
        List<Token> tokens = new ArrayList<>();
        Assertions.assertEquals(-1, TokenCleanUp.getIndexOfNextSemicolon(tokens));
    }
}
