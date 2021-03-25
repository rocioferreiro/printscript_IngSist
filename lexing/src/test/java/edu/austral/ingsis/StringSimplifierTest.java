package edu.austral.ingsis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StringSimplifierTest {

    @Test
    public void testRemoveEntersHappyPath(){
        String text = "'Hola, como estas?' le pregunte yo,\n el reacciono como si hubiese dicho \"Decime como estas o te mato.\"";

        List<Line> expectedResult = new ArrayList<>();
        expectedResult.add(new Line("'Hola, como estas?' le pregunte yo, ", 1));
        expectedResult.add(new Line(" el reacciono como si hubiese dicho \"Decime como estas o te mato.\"", 2));

        Assertions.assertEquals(StringSimplifier.removeEnters(text), expectedResult);
    }
}
