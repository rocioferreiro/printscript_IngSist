package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ConcreteLexer implements Lexer {

    @Override
    public List<Token> scan(Path path) {
        String text = PathReader.read(path);
        List<Line> separatedSentences = StringSimplifier.removeEnters(text);
        separatedSentences = separatedSentences.stream().filter(l -> !l.getText().isEmpty()).collect(toList());
        return separatedSentences.stream().map(this::stringToTokens).flatMap(Collection::stream).collect(toList());
    }

    private List<Token> stringToTokens(Line line){

        //TODO arreglar posiciones (-length)

        List<Token> tokens = new ArrayList<>();
        List<String> separated = StringSimplifier.removeSpaces(line.getText()).stream().filter(s -> !s.isEmpty()).collect(toList());
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
}
