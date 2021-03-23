package edu.austral.ingsis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ConcreteLexer implements Lexer {

    @Override
    public List<Sentence> scan(Path path) {
        String text = toString(path);
        String[] separatedSentences = Arrays.stream(text.split(";")).map(s -> s.replace("\n", "")).toArray(String[]::new);
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
        //TODO tratar entre comillas antes de espacios
        String[] separatedBySpace = txt.split(" ");
        int column = 1;
        for (String s : separatedBySpace) {
            tokens.addAll(getOperatorTokens(s, index, column));
            column += s.length();
        }
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getType().equals(TokenType.invalid)){
                tokens.set(i, KeyWord.findToken(tokens.get(i)));
                if(tokens.get(i).getType().equals(TokenType.invalid)) throw new RuntimeException("Invalid Expresion: " + tokens.get(i).getValue());
            }
        }

        return new Sentence(tokens, index);
    }

    private List<Token> getOperatorTokens(String word, int row, int initialColumn){
        return Operator.findTokens(word, new Position(row, initialColumn));
    }


}
