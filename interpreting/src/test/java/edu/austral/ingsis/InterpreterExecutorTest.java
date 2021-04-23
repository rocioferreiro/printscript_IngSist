//package edu.austral.ingsis;
//
// import java.io.ByteArrayOutputStream;
// import java.io.File;
// import java.io.PrintStream;
// import java.nio.file.Path;
// import org.junit.jupiter.api.*;
//
// public class InterpreterExecutorTest {
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
//    interpreter.interpret(new File("testInterpreterWithNumbersAndStrings.txt"),
// System.out::println);
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
//    interpreter.interpret(new File("testInterpreterWithNumbersAndStrings2.txt"),
// System.out::println);
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
// }
