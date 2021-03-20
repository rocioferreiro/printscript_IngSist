/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.austral.ingsis;

import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Lexer lexer = new ConcreteLexer();
        List<Sentence> sentences = lexer.scan(Paths.get("test.txt"));

        for(Sentence s : sentences){
            System.out.println(s.toString());
        }
    }
}
