package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.List;

public class ConcreteInterpreter implements Interpreter {

  private final Path rules;
  private Lexer lexer;
  private Parser parser;

  // TODO encargarse de Strings tambien

  public ConcreteInterpreter(Path rules) {
    this.rules = rules;
    lexer = new ConcreteLexer();
    parser = new ConcreteParser(rules);
  }

  @Override
  public void interpret(Path code) {
    lexer = new ConcreteLexer();
    List<Token> tokens = lexer.scan(code);
    parser = new ConcreteParser(rules);
    Context context = parser.parse(tokens);
    showContext(context);
  }

  @Override
  public void interpret(String line) {
    List<Token> tokens = lexer.scan(line);
    Context context = parser.parse(tokens);
    showContext(context);
  }

  private void showContext(Context context) {
    for (Variable v : context.getVariables()) {
      System.out.println(v.toString());
    }
  }
}
