package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConcreteInterpreter implements Interpreter {

  private final Path rules;
  private Lexer lexer;
  private Parser parser;
  private Executor executor;
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
    TokenCleanUp.checkLastToken(tokens);
    parser = new ConcreteParser(rules);
    List<Token> sublist = tokens;
    while (!sublist.isEmpty()) {
      int nextIndex = TokenCleanUp.getIndexOfNextSemicolon(sublist);
      ASTInContext ast = parser.parse(new ArrayList<>(sublist.subList(0, nextIndex)));
      context = ast.getContext();
      strategy.execute(executor, ast);
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
}
