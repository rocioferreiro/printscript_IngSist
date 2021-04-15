package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConcreteInterpreter implements Interpreter {

  private final Path rules;
  private Lexer lexer;
  private Parser parser;
  private final Executor executor;
  private Context context;
  private final ExecutionStrategy strategy;

  public ConcreteInterpreter(Path rules, ExecutionStrategy strategy) {
    this.rules = rules;
    this.strategy = strategy;
    lexer = new ConcreteLexer();
    parser = new ConcreteParser(rules);
    executor = new ConcreteExecutor();
    context = new Context();
  }

  @Override
  public void interpret(Path path) {
    context = context.setContexts();
    lexer = new ConcreteLexer();
    List<Token> tokens = lexer.scan(path);
    int amount = TokenCleanUp.getAmountOfSentences(tokens);
    TokenCleanUp.checkLastToken(tokens);
    parser = new ConcreteParser(rules);
    List<Token> sublist = tokens;
    for (int i = 1; i < amount + 1; i++) {
      int nextIndex = getIndexOfNextSeparator(sublist);
      List<Token> list = new ArrayList<>(sublist.subList(0, nextIndex+1));
      if (!contains(list, KeyWord.IF_STATEMENT)) list = new ArrayList<>(list.subList(0, list.size()-1));
      ASTInContext ast = parser.parse(list);
      context = ast.getContext();
      strategy.execute(executor, ast);
      print(amount, i);
      sublist = new ArrayList<>(sublist.subList(nextIndex + 1, sublist.size()));
    }
  }

  private boolean contains(List<Token> tokens, TokenType type) {
    for (Token token : tokens) if (token.getType().equals(type)) return true;
    return false;
  }

  public static int getIndexOfNextSeparator(List<Token> tokens) {
    for (int i = 0; i < tokens.size(); i++) {
      if(tokens.get(i).getType().equals(KeyWord.IF_STATEMENT)){
        return getIndexOfNextSeparatorConditional(tokens, i);
      }
      if (tokens.get(i).getType().equals(Operator.SEMICOLONS)) {
        return i;
      }
    }
    return -1;
  }

  private static int getIndexOfNextSeparatorConditional(List<Token> tokens, int indexOfConditional){
    for (int i = indexOfConditional; i < tokens.size(); i++) {
      if(tokens.get(i).getType().equals(KeyWord.ELSE_STATEMENT)){
        for (int j = i+1; j < tokens.size(); j++) {
          if (tokens.get(j).getType().equals(Operator.R_KEY)) {
            return j;
          }
        }
      }
    }
    for (int i = indexOfConditional; i < tokens.size(); i++) {
      if(tokens.get(i).getType().equals(Operator.L_KEY)){
        for (int j = i+1; j < tokens.size(); j++) {
          if (tokens.get(j).getType().equals(Operator.R_KEY)) {
            return j;
          }
        }
      }
    }
    return -1;
  }

  @Override
  public void interpret(String line) {
    context.setContexts();
    List<Token> tokens = lexer.scan(line);
    tokens = TokenCleanUp.checkLastTokenAndRemove(tokens);
    ASTInContext ast = parser.parse(tokens);
    context = ast.getContext();
    strategy.execute(executor, ast);
  }

  @Override
  public void emptyContext() {
    context.empty();
  }

  private void print(int amountOfLines, int actualLine) {
    String ANSI_RESET = "\u001B[0m";
    String ANSI_BLUE = "\033[0;34m";
    double percentage = ((double) actualLine) / amountOfLines;

    String string =
        "\t".repeat(15)
            + "Interpreting -> ["
            + "#".repeat(actualLine)
            + " ".repeat(amountOfLines - actualLine)
            + "] "
            + (int) (percentage * 100)
            + "%";
    System.out.println(ANSI_BLUE + string + ANSI_RESET);
  }
}
