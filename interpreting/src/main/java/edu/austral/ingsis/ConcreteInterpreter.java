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
  private final Version version;

  public ConcreteInterpreter(Path rules, ExecutionStrategy strategy, Version version) {
    this.rules = rules;
    this.strategy = strategy;
    this.version = version;
    lexer = new ConcreteLexer();
    parser = new ConcreteParser(rules);
    executor = new ConcreteExecutor();
    context = new Context();
  }

  @Override
  public void interpret(Path path) {
    setTokenTypes();
    context = context.setContexts();
    lexer = new ConcreteLexer();
    List<Token> tokens = lexer.scan(path);
    int amount = TokenCleanUp.getAmountOfSentences(tokens);
    TokenCleanUp.checkLastToken(tokens);
    parser = new ConcreteParser(rules);
    List<Token> sublist = tokens;
    int index = 0;
    while (sublist.size() > 0) {
      int nextIndex = getIndexOfNextSeparator(sublist);
      List<Token> list = new ArrayList<>(sublist.subList(0, nextIndex + 1));
      if (!contains(list, KeyWord.IF_STATEMENT) && list.size()>0)
        list = new ArrayList<>(list.subList(0, list.size() - 1));
      ASTInContext ast = parser.parse(list);
      context = ast.getContext();
      strategy.execute(executor, ast);
      print(amount, ++index);
      sublist = new ArrayList<>(sublist.subList(nextIndex + 1, sublist.size()));
    }
  }

  private void setTokenTypes() {
    for (TokenType type : version.getToAccept()) type.setAble(true);
  }

  private boolean contains(List<Token> tokens, TokenType type) {
    for (Token token : tokens) if (token.getType().equals(type)) return true;
    return false;
  }

  public static int getIndexOfNextSeparator(List<Token> tokens) {
    for (int i = 0; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(KeyWord.IF_STATEMENT)) {
        return getIndexOfNextSeparatorConditional(tokens, i);
      }
      if (tokens.get(i).getType().equals(Operator.SEMICOLONS)) {
        return i;
      }
    }
    return -1;
  }

  private static int getIndexOfNextSeparatorConditional(
      List<Token> tokens, int indexOfConditional) {
    for (int i = indexOfConditional; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(KeyWord.ELSE_STATEMENT)) {
        return getEndIndex(tokens, i + 1);
      }
    }
    for (int i = indexOfConditional; i < tokens.size(); i++) {
      if (tokens.get(i).getType().equals(Operator.L_KEY)) {
        return getEndIndex(tokens, i + 1);
      }
    }
    return -1;
  }

  private static int getEndIndex(List<Token> tokens, int initialIndex) {
    int leftKeyCounter = 0;
    for (int j = initialIndex; j < tokens.size(); j++) {
      if (tokens.get(j).getType().equals(Operator.L_KEY)) leftKeyCounter++;
      if (tokens.get(j).getType().equals(Operator.R_KEY)) {
        if (leftKeyCounter > 0) leftKeyCounter--;
        else return j;
      }
    }
    return 0;
  }

  @Override
  public void interpret(String line) {
    setTokenTypes();
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
