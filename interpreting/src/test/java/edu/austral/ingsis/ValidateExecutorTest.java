package edu.austral.ingsis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import org.junit.jupiter.api.*;

public class ValidateExecutorTest {

  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private static final PrintStream originalOut = System.out;
  private static final PrintStream originalErr = System.err;
  private static final Interpreter interpreter =
      new ConcreteInterpreter(
          getPath("rules.txt"), new ValidationExecutionStrategy(), new Version());;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
    interpreter.emptyContext();
    outContent.reset();
  }

  @AfterAll
  public static void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  public void testShowContextHappyPath() {
    interpreter.interpret("let z: string;");
    Assertions.assertEquals("(z, string)\n", outContent.toString());
  }

  @Test
  public void testShowContextChangingType() {
    interpreter.interpret("let x: number;");
    Assertions.assertEquals("(x, number)\n", outContent.toString());
    outContent.reset();
    interpreter.interpret("x = 1;");
    Assertions.assertEquals("(x, number)\n", outContent.toString());
    outContent.reset();
    interpreter.interpret("let y:string = x+\"hola\";");
    Assertions.assertEquals("(x, number)\n(y, string)\n", outContent.toString());
  }

  @Test
  public void testShowContextErrorPrint() {
    Assertions.assertThrows(
        InvalidCodeException.class, () -> interpreter.interpret("let x: number"));
  }

  @Test
  public void testShowContextForPath() {
    interpreter.interpret(getPath("testValidation.txt"));
    String s =
        "(x, string)\n"
            + print(3, 1)
            + "(x, string)\n(y, number)\n"
            + print(3, 2)
            + "(x, string)\n(y, number)\n(noDeclarado, string)\n"
            + print(3, 3);
    Assertions.assertEquals(s, outContent.toString());
  }

  private static Path getPath(String s) {
    ClassLoader classLoader = ValidateExecutorTest.class.getClassLoader();
    File file = new File(classLoader.getResource(s).getFile());
    return file.toPath();
  }

  private String print(int amountOfLines, int actualLine) {
    String ANSI_RESET = "\u001B[0m";
    String ANSI_BLUE = "\033[0;34m";
    double percentage = ((double) actualLine) / amountOfLines;

    String string =
        "\t".repeat(15)
            + "Interpreting -> ["
            + "#".repeat(actualLine)
            + " ".repeat(amountOfLines - actualLine)
            + "] "
            + (int) (percentage * 100)
            + "%";
    return ANSI_BLUE + string + ANSI_RESET + "\n";
  }
}
