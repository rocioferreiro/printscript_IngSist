package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConcreteParser implements Parser {

  private final SyntacticAnalyzer syntacticAnalyzer;
  private final SemanticAnalyzer semanticAnalyzer;

  public ConcreteParser(Path path) {
    this.syntacticAnalyzer = new ConcreteSyntacticAnalyzer(path);
    this.semanticAnalyzer = new ConcreteSemanticAnalyzer();
  }

  @Override
  public Context parse(List<Token> tokens) {
    checkLastToken(tokens);
    List<Token> sublist = tokens;
    while (!sublist.isEmpty()) {
      int nextIndex = getIndexOfNextSemicolon(sublist);
      Sentence sentence = syntacticAnalyzer.analyze(new ArrayList<>(sublist.subList(0, nextIndex)));
      sublist = new ArrayList<>(sublist.subList(nextIndex + 1, sublist.size()));
      semanticAnalyzer.analyze(sentence);
    }
    return semanticAnalyzer.getContext();
  }

  private void checkLastToken(List<Token> tokens) {
    Token lastToken = tokens.get(tokens.size() - 1);
    if (!lastToken.getType().equals(Operator.SEMICOLONS)) {
      throw new InvalidCodeException("Missing last semicolon.", lastToken.getPosition());
    }
  }

  private int getIndexOfNextSemicolon(List<Token> tokens) {
    for (int i = 0; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(Operator.SEMICOLONS)) {
        return i;
      }
    }
    return -1;
  }
}
