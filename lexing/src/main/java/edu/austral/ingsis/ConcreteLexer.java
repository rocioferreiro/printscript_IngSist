package edu.austral.ingsis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ConcreteLexer implements Lexer {

    @Override
    public List<Sentence> scan(Path path) {
        String text = toString(path);
        String[] separatedSentences = text.split(";");
        AtomicInteger index = new AtomicInteger();
        return Arrays.stream(separatedSentences).map(s -> stringToTokens(s, index.incrementAndGet())).collect(Collectors.toList());
    }

    private String toString(Path path){
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Invalid path!");
        }
    }

    private Sentence stringToTokens(String txt, int index){
        List<Token> tokens = new ArrayList<>();
        String accumulator = "";
        for (int i = 0; i < txt.length(); i++) {
            char ch = txt.charAt(i);
            //TODO como mierda manejar espacios y variables y comillas
            Optional<Token> token = KeyWord.findToken(accumulator + ch, new Position(index, i+1));
            if(token.isPresent()){
                tokens.add(token.get());
                accumulator = "";
            } else {
                accumulator += ch;
            }
        }

        return new Sentence(tokens, index);
    }

}
