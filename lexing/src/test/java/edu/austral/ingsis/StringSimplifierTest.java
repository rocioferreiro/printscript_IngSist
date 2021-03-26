package edu.austral.ingsis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StringSimplifierTest {

    // ----------------------------------------- REMOVE ENTERS TESTS -----------------------------------------
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

    @Test
    public void testRemoveEntersLongText() {
        Assertions.assertTimeout(Duration.ofMillis(500), () -> {
            StringSimplifier.removeEnters(LONG_STRING);
        });
    }

    private void testArrayOfLineAreEqual(List<Line> expected, List<Line> result){
        for (int i = 0; i < result.size(); i++) {
            Assertions.assertEquals(result.get(i).getText(), expected.get(i).getText());
            Assertions.assertEquals(result.get(i).getRow(), expected.get(i).getRow());
        }
    }

    // ----------------------------------------- REMOVE SPACES TESTS -----------------------------------------

    @Test
    public void testRemoveSpacesHappyPath(){
        String text = "Hola como va \"entrecomillas\" sigo como si nada";

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Hola");
        expectedResult.add("como");
        expectedResult.add("va");
        expectedResult.add("\"entrecomillas\"");
        expectedResult.add("sigo");
        expectedResult.add("como");
        expectedResult.add("si");
        expectedResult.add("nada");
        
        Assertions.assertArrayEquals(expectedResult.toArray(), StringSimplifier.removeSpaces(text).toArray());
    }

    @Test
    public void testRemoveSpacesWithStaceInString(){
        String text = "Hola como va \"hago una aclaracion\" sigo como si nada";

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Hola");
        expectedResult.add("como");
        expectedResult.add("va");
        expectedResult.add("\"hago una aclaracion\"");
        expectedResult.add("sigo");
        expectedResult.add("como");
        expectedResult.add("si");
        expectedResult.add("nada");

        Assertions.assertArrayEquals(expectedResult.toArray(), StringSimplifier.removeSpaces(text).toArray());
    }

    @Test
    public void testRemoveSpacesWithSpacesInBeginning(){
        String text = "   Hola como va \"hago una aclaracion\" sigo como si nada";

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Hola");
        expectedResult.add("como");
        expectedResult.add("va");
        expectedResult.add("\"hago una aclaracion\"");
        expectedResult.add("sigo");
        expectedResult.add("como");
        expectedResult.add("si");
        expectedResult.add("nada");

        Assertions.assertArrayEquals(expectedResult.toArray(), StringSimplifier.removeSpaces(text).toArray());
    }

    @Test
    public void testRemoveSpacesWithSpacesInEnd(){
        String text = "Hola como va \"hago una aclaracion\" sigo como si nada      ";

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Hola");
        expectedResult.add("como");
        expectedResult.add("va");
        expectedResult.add("\"hago una aclaracion\"");
        expectedResult.add("sigo");
        expectedResult.add("como");
        expectedResult.add("si");
        expectedResult.add("nada");

        Assertions.assertArrayEquals(expectedResult.toArray(), StringSimplifier.removeSpaces(text).toArray());
    }

    @Test
    public void testRemoveSpacesWithMultipleMiddleSpaces(){
        String text = "Hola como    va \"hago una aclaracion\" sigo          como si nada";

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Hola");
        expectedResult.add("como");
        expectedResult.add("va");
        expectedResult.add("\"hago una aclaracion\"");
        expectedResult.add("sigo");
        expectedResult.add("como");
        expectedResult.add("si");
        expectedResult.add("nada");

        Assertions.assertArrayEquals(expectedResult.toArray(), StringSimplifier.removeSpaces(text).toArray());
    }

    @Test
    public void testRemoveSpacesLongText() {
        Assertions.assertTimeout(Duration.ofMillis(500), () -> {
            StringSimplifier.removeSpaces(LONG_STRING);
        });
    }



    // ----------------------------------------------- LONG TEXT -----------------------------------------------
    private final String LONG_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque lectus eros, " +
            "lobortis et cursus dapibus, placerat vel 'libero'. Nulla facilisi. Duis suscipit ex eget leo vestibulum rhoncus." +
            " Sed tincidunt urna vel hendrerit faucibus. In porttitor tempor ligula, eget facilisis erat tempus eu. Quisque" +
            " egestas tortor sed eros placerat, eu facilisis dui \n pulvinar. 'Sed' at hendrerit turpis, eget sodales diam. Nunc" +
            " egestas tellus sapien, et 'vestibulum enim sollicitudin at. Ves'tibulum vel' blandit est, et feugiat metus." +
            " Curabitur eu viverra nibh, et maximus tellus. Praesent placerat \"turpis id turpis\" tempor rhoncus. Integer " +
            "sit amet sollicitudin nisi, eu blandit arcu. Nam viverra, justo nec facilisis vulputate, eros erat interdum" +
            " felis, at gravida arcu est ut turpis. Sed vel \"metus eu arcu faucibus\" euismod. Nulla efficitur elementum" +
            " lectus non fermentum.\n" +
            "\n" +
            "Fusce sed venenatis felis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus consequat " +
            "maximus dolor quis ultrices. Aenean ac urna sit amet nisl semper pharetra. Integer interdum magna sem, in " +
            "varius urna consequat non. 'Mauris ultrices', nisi vel accumsan maximus, enim libero efficitur odio, sit amet" +
            " venenatis leo massa quis dolor. In augue sem, scelerisque ac \"eleifend\" quis, molestie et massa. Vivamus sed" +
            " consectetur velit. Mauris non tortor sit amet erat \n vulputate congue. Mauris lacus nulla, semper eget " +
            "gravida sit amet, \"condimentum nec magna. Sed luctus lectus quam, ut 'elementum' sapien pulvinar a. Curabitur" +
            " lacinia orci id turpis tempor, eu viverra ex\" ornare. Pellentesque imperdiet leo 'vitae' augue laoreet " +
            "auctor. Phasellus at nunc eget leo pretium imperdiet a vel massa. In 'et' ipsum rutrum, consequat tortor a," +
            " aliquam velit. Quisque iaculis sapien quis nibh faucibus, non tincidunt lorem 'vulputate.\n" +
            "\n" +
            "Maecenas' egestas ullamcorper nunc, at 'convallis' erat accumsan non. Fusce pulvinar, odio ut posuere " +
            "condimentum, nulla \"ri\nsus\" vehicula dui, ac ultricies eros 'enim' a nunc. Ut lacinia sit amet sem non tincidunt. " +
            "Cras leo neque, imperdiet aliquet lobortis 'ac, varius ut dui. Nam' at dui at sem 'tincidunt' suscipit a vitae " +
            "dui. Pellentesque lobortis suscipit ante, a vehicula \n sapien condimentum 'in'. Curabitur ut ipsum ut libero " +
            "pellentesque \"fermentum\" ac nec velit. Nunc dui risus, dapibus quis risus vitae, \"congue\" porta nisl. Aliquam " +
            "imperdiet facilisis orci, et consectetur mauris dignissim quis. Sed eu tristique erat. Suspendisse id leo " +
            "laoreet, varius nunc eu, rutrum tellus. Suspendisse potenti.\n" +
            "\n" +
            "Aenean dapibus gravida dui, \"non condimentum justo rutrum\" sed. Nam mi lacus, vestibulum sed 'libero' at, " +
            "auctor finibus 'sapien'. In hac habitasse platea dictumst. Donec fermentum felis 'sed; gravida maximus. " +
            "Suspendisse aliquam arcu id blandit ullamcorper.\n  Praesent vulputate felis vel quam pharetra molestie. " +
            "Nulla facilisi. Nunc eget 'dictum dui. Aliquam molestie risus' 'sem', pretium scelerisque nunc vestibulum nec." +
            " Phasellus iaculis metus dignissim\" ante tincidu\"nt iaculis. Phasellus eu risus mi. Aliquam at metus " +
            "ultricies, tempor sapien mollis, varius erat.\n" +
            "\n" +
            "Sed pretium tortor id mauris pharetra tristique. Fusce 'blandit' risus tortor, sit amet blandit est vehicula" +
            " sit amet. Mauris \"ante metus, faucibus euismod ultricies\" et, dictum nec tortor. Sed fringilla ultricies" +
            " massa, eu sodales purus sodales et. Nunc lectus massa, \n convallis 'nec sollicitudin vel', tincidunt sit amet" +
            " lectus. Sed eget eros laoreet, blandit orci ut, facilisis tellus. Praesent pretium.";
}
