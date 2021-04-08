package edu.austral.ingsis;

import static java.util.stream.Collectors.toList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConcreteLexer implements Lexer {

  @Override
  public List<Token> scan(Path path) {
    String text = PathReader.read(path);
    List<Line> separatedSentences = StringSimplifier.removeEnters(text);
    separatedSentences =
        separatedSentences.stream().filter(l -> !l.getText().isEmpty()).collect(toList());
    return separatedSentences.stream()
        .map(this::stringToTokens)
        .flatMap(Collection::stream)
        .collect(toList());
  }

  @Override
  public List<Token> scan(String line) {
    return stringToTokens(new Line(line, 1));
  }

  private List<Token> stringToTokens(Line line) {
    List<Token> tokens = new ArrayList<>();
    List<String> separated = new ArrayList<>(StringSimplifier.removeSpaces(line.getText()));
    int column = 1;
    List<String> stringDelimiters = StringSimplifier.getStringDelimiters();
    for (String s : separated) {
      if (stringDelimiters.stream().anyMatch(s::contains))
        tokens.add(new ProvisionalToken(s, new Position(line.getRow(), column)));
      else tokens.addAll(getOperatorTokens(s, line.getRow(), column));
      column += s.length();
    }
    for (int i = 0; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(TokenType.invalid)) {
        tokens.set(i, KeyWord.findToken(tokens.get(i)));
        if (tokens.get(i).getType().equals(TokenType.invalid))
          throw new InvalidCodeException(
              "Invalid Expresion: " + tokens.get(i).getValue() + " in:" + line.getText(),
              new Position(line.getRow(), 1));
      }
    }
    return tokens;
  }

  private List<Token> getOperatorTokens(String word, int row, int initialColumn) {
    return Operator.findTokens(word, new Position(row, initialColumn));
  }
}
