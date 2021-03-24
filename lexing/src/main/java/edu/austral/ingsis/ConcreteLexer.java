package edu.austral.ingsis;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

public class ConcreteLexer implements Lexer {

    @Override
    public List<Token> scan(Path path) {
        String text = toString(path);
        List<Line> separatedSentences = checkForEntersInColons(text);
        separatedSentences = separatedSentences.stream().filter(l -> !l.getText().isEmpty()).collect(toList());
        return separatedSentences.stream().map(this::stringToTokens).flatMap(Collection::stream).collect(toList());
    }

    private String toString(Path path){
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Invalid path!");
        }
    }

    private List<Token> stringToTokens(Line line){

        //TODO arreglar posiciones (-length)

        List<Token> tokens = new ArrayList<>();
        List<String> separated = checkForSpacesInColons(line.getText()).stream().filter(s -> !s.isEmpty()).collect(toList());
        int column = 1;
        for (String s : separated) {
            tokens.addAll(getOperatorTokens(s, line.getRow(), column));
            column += s.length();
        }
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getType().equals(TokenType.invalid)){
                tokens.set(i, KeyWord.findToken(tokens.get(i)));
                if(tokens.get(i).getType().equals(TokenType.invalid)) throw new RuntimeException("Invalid Expresion: "
                        + tokens.get(i).getValue() + " in:" + line.getText());
            }
        }

        return tokens;
    }

    private List<Token> getOperatorTokens(String word, int row, int initialColumn){
        return Operator.findTokens(word, new Position(row, initialColumn));
    }

    //TODO comillas simples
    private List<String> checkForSpacesInColons(String s) {
        List<String> split = new ArrayList<>();
        String nonChecked = s;
        while (nonChecked.contains("\"")) {
            String subBetween =  StringUtils.substringBetween(nonChecked, "\"");
            if (subBetween == null) {
                throw new RuntimeException("Missing one \" in " + s);
            }
            String between = "\"" + subBetween + "\"";
            String[] aux = split(nonChecked, between).toArray(String[]::new);
            nonChecked = aux.length > 1 ? aux[1] : "";
            split.addAll(Arrays.asList(aux[0].strip().split(" ")));
            split.add(between);
        }
        if (nonChecked.equals(s)) split.addAll(Arrays.asList(s.split(" ")));
        else {
            split.addAll(Arrays.asList(nonChecked.strip().split(" ")));
        }
        return split;
    }

    private List<Line> checkForEntersInColons(String s) {
        List<Line> result = new ArrayList<>();
        AtomicInteger row = new AtomicInteger(1);
        String nonChecked = s;
        while (nonChecked.contains("\"")) {
            String subBetween =  StringUtils.substringBetween(nonChecked, "\"");
            if (subBetween == null) {
                throw new RuntimeException("Missing one \" in " + s);
            }
            String between = "\"" + subBetween + "\"";
            String[] aux = split(nonChecked, between).toArray(String[]::new);
            nonChecked = aux.length > 1 ? aux[1] : "";
            if(!result.isEmpty()){
                row.decrementAndGet();
                List<Line> newLines = Arrays.stream(aux[0].split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList());

                result = mergeLists(result, newLines);
            }
            else {
                result = Arrays.stream(aux[0].split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList());
            }
            if(aux[0].charAt(aux[0].length()-1) == '\n') {
                result.add(new Line(between, row.getAndIncrement()));
            } else {
                result.set(result.size()-1, result.get(result.size()-1).concatText(between));
            }
        }
        if (nonChecked.equals(s)) result.addAll(Arrays.stream(s.split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList()));
        else{
            row.decrementAndGet();
            List<Line> last = Arrays.stream(nonChecked.split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList());
            result = mergeLists(result, last);
        }
        return result;
    }

    private List<Line> mergeLists(List<Line> oldLines,List<Line> newLines){
        List<Line> result = oldLines;
        result.set(result.size()-1, result.get(result.size()-1).concatText(newLines.get(0).getText()));
        result.addAll(newLines.subList(1, newLines.size()));
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
