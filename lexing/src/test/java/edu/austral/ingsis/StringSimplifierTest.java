package edu.austral.ingsis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StringSimplifierTest {

    @Test
    public void testRemoveEntersHappyPath(){
        String text = "Fue asi: 'Hola, como estas?' le pregunte yo,\n el reacciono como si hubiese dicho \"Decime como estas o te mato.\"";

        List<Line> expectedResult = new ArrayList<>();
        expectedResult.add(new Line("Fue asi: 'Hola, como estas?' le pregunte yo,", 1));
        expectedResult.add(new Line(" el reacciono como si hubiese dicho \"Decime como estas o te mato.\"", 2));

        testArrayOfLineAreEqual(expectedResult, StringSimplifier.removeEnters(text));
    }

    @Test
    public void testRemoveEntersWithEntersInString(){
        String text = "Fue asi: 'Hola, \n como estas?' le pregunte yo,\n el reacciono como si hubiese dicho \"Decime como \n estas o te mato.\"";

        List<Line> expectedResult = new ArrayList<>();
        expectedResult.add(new Line("Fue asi: 'Hola, \n como estas?' le pregunte yo,", 1));
        expectedResult.add(new Line(" el reacciono como si hubiese dicho \"Decime como \n estas o te mato.\"", 2));

        testArrayOfLineAreEqual(expectedResult, StringSimplifier.removeEnters(text));
    }

    @Test
    public void testRemoveEntersWithEntersInBeginning(){
        String text = "\n\nFue asi: 'Hola, como estas?' le pregunte yo,\n el reacciono como si hubiese dicho \"Decime como estas o te mato.\"";

        List<Line> expectedResult = new ArrayList<>();
        expectedResult.add(new Line("", 1));
        expectedResult.add(new Line("", 2));
        expectedResult.add(new Line("Fue asi: 'Hola, como estas?' le pregunte yo,", 3));
        expectedResult.add(new Line(" el reacciono como si hubiese dicho \"Decime como estas o te mato.\"", 4));

        testArrayOfLineAreEqual(expectedResult, StringSimplifier.removeEnters(text));
    }

    @Test
    public void testRemoveEntersWithEntersInEnd(){
        String text = "Fue asi: 'Hola, como estas?' le pregunte yo,\n el reacciono como si hubiese dicho \"Decime como estas o te mato.\"\n\n\n";

        List<Line> expectedResult = new ArrayList<>();
        expectedResult.add(new Line("Fue asi: 'Hola, como estas?' le pregunte yo,", 1));
        expectedResult.add(new Line(" el reacciono como si hubiese dicho \"Decime como estas o te mato.\"", 2));
        expectedResult.add(new Line("", 3));
        expectedResult.add(new Line("", 4));
        expectedResult.add(new Line("", 5));

        testArrayOfLineAreEqual(expectedResult, StringSimplifier.removeEnters(text));
    }

    @Test
    public void testRemoveEntersWithEmptyMiddleLines(){
        String text = "Fue asi: 'Hola, como estas?' le pregunte yo,\n el reacciono \n\n\n como si hubiese dicho \"Decime como estas o te mato.\"";

        List<Line> expectedResult = new ArrayList<>();
        expectedResult.add(new Line("Fue asi: 'Hola, como estas?' le pregunte yo,", 1));
        expectedResult.add(new Line(" el reacciono ", 2));
        expectedResult.add(new Line("", 3));
        expectedResult.add(new Line("", 4));
        expectedResult.add(new Line(" como si hubiese dicho \"Decime como estas o te mato.\"", 5));

        testArrayOfLineAreEqual(expectedResult, StringSimplifier.removeEnters(text));
    }


    private void testArrayOfLineAreEqual(List<Line> expected, List<Line> result){
        for (int i = 0; i < result.size(); i++) {
            Assertions.assertEquals(result.get(i).getText(), expected.get(i).getText());
            Assertions.assertEquals(result.get(i).getRow(), expected.get(i).getRow());
        }
    }


}
