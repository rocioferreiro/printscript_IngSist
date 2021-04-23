package edu.austral.ingsis;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConcreteInterpreter implements Interpreter {

  private final Path rules;
  private Lexer lexer = new ConcreteLexer();
  private Parser parser;
  private final Executor executor = new ConcreteExecutor();
  private Context context = new Context();
  private final ExecutionStrategy strategy;
  private final Version version;
  private final List<Version> versions;

  public ConcreteInterpreter(String version, File rules) {
    this.rules = Path.of(rules.getPath());
    this.strategy = new InterpretationExecutionStrategy();
    this.versions = setVersions();
    this.version = getVersion(version);
  }

  public ConcreteInterpreter(ExecutionStrategy strategy, String version, Path rules) {
    this.rules = rules;
    this.strategy = strategy;
    this.versions = setVersions();
    this.version = getVersion(version);
    parser = new ConcreteParser(rules);
  }

  public ConcreteInterpreter(ExecutionStrategy strategy, String version) {
    this.rules = Paths.get("rules.txt");
    this.strategy = strategy;
    this.versions = setVersions();
    this.version = getVersion(version);
    parser = new ConcreteParser(rules);
  }

  @Override
  public void interpret(File file, Consumer<String> out, boolean progress) {
    setTokenTypes();
    context = context.setContexts();
    lexer = new ConcreteLexer();
    List<Token> tokens = lexer.scan(file);
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
      ast.getContext().setOut(out);
      context = ast.getContext();
      strategy.execute(executor, ast);
      if(progress) print(amount, ++index);
      sublist = new ArrayList<>(sublist.subList(nextIndex + 1, sublist.size()));
    }
  }

  @Override
  public void interpret(String line, Consumer<String> out) {
    setTokenTypes();
    context.setContexts();
    List<Token> tokens = lexer.scan(line);
    tokens = TokenCleanUp.checkLastTokenAndRemove(tokens);
    ASTInContext ast = parser.parse(tokens);
    ast.getContext().setOut(out);
    context = ast.getContext();
    strategy.execute(executor, ast);
  }

  @Override
  public void emptyContext() {
    context.empty();
  }

  private Version getVersion(String version) {
    for (Version v : versions) if (v.getName().equals(version)) return v;
    return new Version();
  }

  private List<Version> setVersions() {
    List<Version> versions = new ArrayList<>();
    versions.add(new Version("PrintScript 1.0", new ArrayList<>()));
    TokenType[] typesPrint1 = {
      KeyWord.B_ASSIGNATION,
      KeyWord.C_DECLARATION,
      KeyWord.BOOLEAN,
      KeyWord.IF_STATEMENT,
      KeyWord.ELSE_STATEMENT,
      Operator.EQUAL_EQUAL,
      Operator.GREATER_EQUAL,
      Operator.MINOR_EQUAL,
      Operator.GREATER,
      Operator.MINOR,
      Operator.L_KEY,
      Operator.R_KEY
    };
    versions.add(new Version("PrintScript 1.1", Arrays.asList(typesPrint1)));
    return versions;
  }

  private void setTokenTypes() {
    for (TokenType type : version.getToAccept()) type.setAble(true);
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
