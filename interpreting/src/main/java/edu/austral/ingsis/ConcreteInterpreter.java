package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.List;

public class ConcreteInterpreter implements Interpreter {

  private final Path code;
  private final Path rules;

    //TODO encargarse de Strings tambien
    
  public ConcreteInterpreter(Path code, Path rules) {
    this.code = code;
    this.rules = rules;
  }

  @Override
  public void interpret() {
    Lexer lexer = new ConcreteLexer();
    List<Token> tokens = lexer.scan(code);
    Parser parser = new ConcreteParser(rules);
    Context context = parser.parse(tokens);
    showContext(context);
  }

  private void showContext(Context context) {
    for (Variable v : context.getVariables()) {
      System.out.println(v.toString());
    }
  }
}
