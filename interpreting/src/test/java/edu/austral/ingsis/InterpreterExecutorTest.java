package edu.austral.ingsis;

//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.PrintStream;
//import java.nio.file.Path;
//import org.junit.jupiter.api.*;
//
//public class InterpreterExecutorTest {
//
//  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//  private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
//  private static final PrintStream originalOut = System.out;
//  private static final PrintStream originalErr = System.err;
//  private static final Interpreter interpreter =
//      new ConcreteInterpreter(
//          getPath("rules.txt"), new InterpretationExecutionStrategy(), "PrintScript 1.0");
//
//  @BeforeEach
//  public void setUpStreams() {
//    System.setOut(new PrintStream(outContent));
//    System.setErr(new PrintStream(errContent));
//    interpreter.emptyContext();
//    outContent.reset();
//  }
//
//  @AfterAll
//  public static void restoreStreams() {
//    System.setOut(originalOut);
//    System.setErr(originalErr);
//  }
//
//  @Test
//  public void testInterpreterInPathWithNumbers() {
//    interpreter.interpret(new File("testInterpreterWithNumbers.txt"), System.out::println);
//    String s =
//        print(10, 1)
//            + "12\n"
//            + print(10, 2)
//            + print(10, 3)
//            + "27.0\n"
//            + print(10, 4)
//            + print(10, 5)
//            + "15.0\n"
//            + print(10, 6)
//            + print(10, 7)
//            + "30.0\n"
//            + print(10, 8)
//            + print(10, 9)
//            + "3.0\n"
//            + print(10, 10);
//    Assertions.assertEquals(s, outContent.toString());
//  }
//
//  @Test
//  public void testInterpreterInPathWithStrings() {
//    interpreter.interpret(new File("testInterpreterWithStrings.txt"), System.out::println);
//    String s =
//        print(10, 1)
//            + "hola\n"
//            + print(10, 2)
//            + print(10, 3)
//            + "hola como\n"
//            + print(10, 4)
//            + print(10, 5)
//            + "hla cm\n"
//            + print(10, 6)
//            + print(10, 7)
//            + "holoao  como\n"
//            + print(10, 8)
//            + print(10, 9)
//            + "ho o  como\n"
//            + print(10, 10);
//    Assertions.assertEquals(s, outContent.toString());
//  }
//
//  @Test
//  public void testInterpreterInPathWithNumbersAndStrings() {
//    interpreter.interpret(new File("testInterpreterWithNumbersAndStrings.txt"), System.out::println);
//    String s =
//        print(10, 1)
//            + "hola\n"
//            + print(10, 2)
//            + print(10, 3)
//            + "hola12\n"
//            + print(10, 4)
//            + print(10, 5)
//            + "ho\n"
//            + print(10, 6)
//            + print(10, 7)
//            + "hohoho\n"
//            + print(10, 8)
//            + print(10, 9)
//            + "hohoh o\n"
//            + print(10, 10);
//    Assertions.assertEquals(s, outContent.toString());
//  }
//
//  @Test
//  public void testInterpreterInPathWithNumbersAndStrings2() {
//    interpreter.interpret(new File("testInterpreterWithNumbersAndStrings2.txt"), System.out::println);
//    String s =
//        print(10, 1)
//            + "hola\n"
//            + print(10, 2)
//            + print(10, 3)
//            + "12hola\n"
//            + print(10, 4)
//            + print(10, 5)
//            + "12\n"
//            + print(10, 6)
//            + print(10, 7)
//            + "121212\n"
//            + print(10, 8)
//            + print(10, 9)
//            + "12121 2\n"
//            + print(10, 10);
//    Assertions.assertEquals(s, outContent.toString());
//  }
//
//  @Test
//  public void testInterpreterInLine() {
//    interpreter.interpret("let x:string = 'hola';", System.out::println);
//    Assertions.assertEquals("", outContent.toString());
//  }
//
//  private String print(int amountOfLines, int actualLine) {
//    String ANSI_RESET = "\u001B[0m";
//    String ANSI_BLUE = "\033[0;34m";
//    double percentage = ((double) actualLine) / amountOfLines;
//
//    String string =
//        "\t".repeat(15)
//            + "Interpreting -> ["
//            + "#".repeat(actualLine)
//            + " ".repeat(amountOfLines - actualLine)
//            + "] "
//            + (int) (percentage * 100)
//            + "%";
//    return ANSI_BLUE + string + ANSI_RESET + "\n";
//  }
//
//  private static Path getPath(String s) {
//    ClassLoader classLoader = ValidateExecutorTest.class.getClassLoader();
//    File file = new File(classLoader.getResource(s).getFile());
//    return file.toPath();
//  }
//}
