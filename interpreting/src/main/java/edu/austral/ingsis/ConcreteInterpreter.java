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
  public void interpret(Path code) {
    lexer = new ConcreteLexer();
    List<Token> tokens = lexer.scan(code);
    parser = new ConcreteParser(rules);
    checkLastToken(tokens);
    List<Token> sublist = tokens;
    while (!sublist.isEmpty()) {
      int nextIndex = getIndexOfNextSemicolon(sublist);
      ASTInContext ast = parser.parse(new ArrayList<>(sublist.subList(0, nextIndex)));
      context = ast.getContext();
      executor.execute(ast.getAst(), context);
      sublist = new ArrayList<>(sublist.subList(nextIndex + 1, sublist.size()));
    }
  }

  @Override
  public void interpret(String line) {
    List<Token> tokens = lexer.scan(line);
    ASTInContext ast = parser.parse(tokens);
    context = ast.getContext();
    executor.execute(ast.getAst(), context);
  }

  @Override
  public void emptyContext() {
    context.empty();
  }

  //  private void showContext() {
  //    for (Variable v : context.getVariables()) {
  //      System.out.println(v.toString());
  //    }
  //  }

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
