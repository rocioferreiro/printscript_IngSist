package edu.austral.ingsis;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcreteLexer implements Lexer {

    @Override
    public List<Sentence> scan(Path path) {
        String text = toString(path);
        String[] separatedSentences = Arrays.stream(text.split(";")).map(this::checkForEntersInColons).toArray(String[]::new);
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
        List<String> separated = checkForSpacesInColons(txt);
//        String[] separatedBySpace = txt.split(" ");
        int column = 1;
        for (String s : separated) {
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

    private List<String> checkForSpacesInColons(String s) {
        List<String> split = new ArrayList<>();
        String nonChecked = s;
        while (nonChecked.contains("\"")) {
            String between = "\"" + StringUtils.substringBetween(nonChecked, "\"") + "\"";
            if (between == null) {
                throw new RuntimeException("Missing one \"");
            }
            String[] aux = split(nonChecked, between);
            nonChecked = aux.length > 1 ? aux[1] : "";
            String[][] aux2 = Arrays.stream(aux).map(a -> a.split(" ")).toArray(String[][]::new);
            split.addAll(Arrays.asList(aux2[0]));
            split.add(between);
        }
        if (nonChecked.equals(s)) split.addAll(Arrays.asList(s.split(" ")));
        return split;
    }

    private String checkForEntersInColons(String s) {
        String result = "";
        String nonChecked = s;
        while (nonChecked.contains("\"")) {
            String between = "\"" + StringUtils.substringBetween(nonChecked, "\"") + "\"";
            if (between == null) {
                throw new RuntimeException("Missing one \" in " + s);
            }
            String[] aux = split(nonChecked, between);
            nonChecked = aux.length > 1 ? aux[1] : "";
            result += aux[0].replace("\n", "");
            result += between;
        }
        if (nonChecked.equals(s)) result = s.replace("\n", "");
        return result;
    }

    private String[] split(String txt, String value) {
        String[] array = new String[2];
        if (txt.contains(value)) {
            array[0] = txt.substring(0, txt.indexOf('\"'));
            String s = txt.substring(txt.indexOf('\"')+1);
            array[1] = s.substring(s.indexOf('\"')+1);
        } else {
            array[0] = value;
        }
        return array;
    }
}
