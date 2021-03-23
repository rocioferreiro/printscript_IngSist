package edu.austral.ingsis;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

public class ConcreteLexer implements Lexer {

    @Override
    public List<Sentence> scan(Path path) {
        String text = toString(path);
        String[] separatedSentences = Arrays.stream(text.split(";")).map(this::checkForEntersInColons).toArray(String[]::new);
        separatedSentences = Arrays.stream(separatedSentences).filter(l -> !l.isEmpty()).toArray(String[]::new);
        AtomicInteger index = new AtomicInteger();
        return Arrays.stream(separatedSentences).map(s -> stringToTokens(s, index.incrementAndGet())).collect(toList());
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
        List<String> separated = checkForSpacesInColons(txt);
        int column = 1;
        for (String s : separated) {
            tokens.addAll(getOperatorTokens(s, index, column));
            column += s.length();
        }
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getType().equals(TokenType.invalid)){
                tokens.set(i, KeyWord.findToken(tokens.get(i)));
                if(tokens.get(i).getType().equals(TokenType.invalid)) throw new RuntimeException("Invalid Expresion: " + tokens.get(i).getValue() + " in:" + txt);
            }
        }

        return new Sentence(tokens, index);
    }

    private List<Token> getOperatorTokens(String word, int row, int initialColumn){
        return Operator.findTokens(word, new Position(row, initialColumn));
    }

    //TODO comillas simples
    private List<String> checkForSpacesInColons(String s) {
        List<String> split = new ArrayList<>();
        String nonChecked = s;
        while (nonChecked.contains("\"")) {
            String between = "\"" + StringUtils.substringBetween(nonChecked, "\"") + "\"";
            if (between == null) {
                throw new RuntimeException("Missing one \"");
            }
            String[] aux = split(nonChecked, between).toArray(String[]::new);
            nonChecked = aux.length > 1 ? aux[1] : "";
            split.addAll(Arrays.asList(aux[0].strip().split(" ")));
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
            String[] aux = split(nonChecked, between).toArray(String[]::new);
            nonChecked = aux.length > 1 ? aux[1] : "";
            result += aux[0].replace("\n", "");
            result += between;
        }
        if (nonChecked.equals(s)) result = s.replace("\n", "");
        else result += nonChecked.replace("\n", "");
        return result;
    }

    private List<String> split(String txt, String value) {
        List<String> array = new ArrayList<>();
        if (txt.contains(value)) {
            array.add(txt.substring(0, txt.indexOf('\"')));
            String s = txt.substring(txt.indexOf('\"')+1);
            if(!s.substring(s.indexOf('\"')+1).isEmpty()) array.add(s.substring(s.indexOf('\"')+1));
        } else {
            array.add(value);
        }
        return array;
    }
}
