package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.List;

public class ConcreteInterpreter implements Interpreter {

  private final Path rules;
  private Lexer lexer;
  private Parser parser;
  private Context context;
  private final ExecutionStrategy strategy;

  public ConcreteInterpreter(Path rules, ExecutionStrategy strategy) {
    this.rules = rules;
    this.strategy = strategy;
    lexer = new ConcreteLexer();
    parser = new ConcreteParser(rules);
    context = new Context();
  }

  @Override
  public void interpret(Path code) {
    lexer = new ConcreteLexer();
    List<Token> tokens = lexer.scan(code);
    parser = new ConcreteParser(rules);
    context = parser.parse(tokens);
    showContext();
  }

  @Override
  public void interpret(String line) {
    List<Token> tokens = lexer.scan(line);
    context = parser.parse(tokens);
    showContext();
  }

  @Override
  public void emptyContext() {
    context.empty();
  }

  private void showContext() {
    for (Variable v : context.getVariables()) {
      System.out.println(v.toString());
    }
  }
}
