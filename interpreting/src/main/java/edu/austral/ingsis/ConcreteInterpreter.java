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
      int nextIndex = TokenCleanUp.getIndexOfNextSeparator(sublist);
      List<Token> list = new ArrayList<>(sublist.subList(0, nextIndex + 1));
      if (!TokenCleanUp.contains(list, KeyWord.IF_STATEMENT) && list.size() > 0)
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
