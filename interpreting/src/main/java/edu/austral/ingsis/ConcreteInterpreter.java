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
    lexer = new ConcreteLexer();
    List<Token> tokens = lexer.scan(path);
    int amount = TokenCleanUp.getAmountOfSentences(tokens);
    TokenCleanUp.checkLastToken(tokens);
    parser = new ConcreteParser(rules);
    List<Token> sublist = tokens;
    for (int i = 1; i < amount+1; i++) {
      int nextIndex = TokenCleanUp.getIndexOfNextSemicolon(sublist);
      ASTInContext ast = parser.parse(new ArrayList<>(sublist.subList(0, nextIndex)));
      context = ast.getContext();
      strategy.execute(executor, ast);
      print(amount, i);
      sublist = new ArrayList<>(sublist.subList(nextIndex + 1, sublist.size()));
    }
  }

  @Override
  public void interpret(String line) {
    List<Token> tokens = lexer.scan(line);
    ASTInContext ast = parser.parse(TokenCleanUp.checkLastTokenAndRemove(tokens));
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
    double percentage = ((double)actualLine)/amountOfLines;

    String string = "\t".repeat(15) + "Interpreting -> [" + "#".repeat(actualLine) +
            " ".repeat(amountOfLines - actualLine) +
            "] " + (int) (percentage*100) + "%";
    System.out.println(ANSI_BLUE + string + ANSI_RESET);
  }
}
