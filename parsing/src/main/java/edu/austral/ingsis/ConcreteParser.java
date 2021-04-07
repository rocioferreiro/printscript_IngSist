package edu.austral.ingsis;

import java.nio.file.Path;
import java.util.List;

public class ConcreteParser implements Parser {

  private final SyntacticAnalyzer syntacticAnalyzer;
  private final SemanticAnalyzer semanticAnalyzer;

  public ConcreteParser(Path path) {
    this.syntacticAnalyzer = new ConcreteSyntacticAnalyzer(path);
    this.semanticAnalyzer = new ConcreteSemanticAnalyzer();
  }

  @Override
  public ASTInContext parse(List<Token> tokens) {
    ASTWrapper ast = syntacticAnalyzer.analyze(tokens);
    semanticAnalyzer.analyze(ast);
    return new ASTInContext(ast.getTree(), semanticAnalyzer.getContext());
  }
}
