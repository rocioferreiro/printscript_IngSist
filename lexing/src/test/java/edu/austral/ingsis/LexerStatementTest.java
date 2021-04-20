package edu.austral.ingsis;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(Parameterized.class)
public class LexerStatementTest {

    private final Lexer lexer = new ConcreteLexer();

    @SuppressWarnings("WeakerAccess")
    @Parameterized.Parameter(value = 0)
    public String version;

    @SuppressWarnings("WeakerAccess")
    @Parameterized.Parameter(value = 1)
    public String directory;

    @Parameterized.Parameters(name = "version {0} - {1})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1.0", "happy-path"}
//                {"1.0", "no-enters"},
//                {"1.0", "number-case"},
//                {"1.0", "string-case"},
//                {"1.1", "boolean-case"}
        });
    }

    @Test
    public void testPrintStatement() throws FileNotFoundException {
        String testDirectory = "src/test/java/resources/" + version + "/" + directory + "/";
        Path srcPath = Path.of(testDirectory + "main.ps");
        List<String> expectedOutput = readLines(testDirectory + "output.txt");
        List<String> actualOutput = Serializer.serialize(lexer.scan(srcPath));
        // PrintCollector printCollector = new PrintCollector();
        // ErrorCollector errorCollector = new ErrorCollector();
        // interpreter.execute(srcFile, version, printCollector, errorCollector);
        assertEquals(actualOutput, expectedOutput);
//        assertThat(errorCollector.getErrors(), is(Collections.emptyList()));
//        assertThat(printCollector.getMessages(), is(expectedOutput));
    }

    private List<String> readLines(String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file));
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNextLine()){
            list.add(s.nextLine());
        }
        s.close();
        return list;
    }

}
