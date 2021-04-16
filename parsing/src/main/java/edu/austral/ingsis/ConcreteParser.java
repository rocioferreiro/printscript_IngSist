package edu.austral.ingsis;

import edu.austral.ingsis.rules.RuleType;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    AST tree;
    if (ast.getType().equals(RuleType.IF)) tree = convertWrapper(ast);
    else tree = ast.getTree();
    return new ASTInContext(tree, semanticAnalyzer.getContext());
  }

  private AST convertWrapper(ASTWrapper wrapper){
    if(wrapper.getLeft() == null && wrapper.getRight()==null) return wrapper.getTree();
    List<ASTWrapper> left = wrapper.getLeft();
    List<ASTWrapper> right = wrapper.getRight();
    wrapper.getTree().setLeftIf(left.stream().map(this::convertWrapper).collect(Collectors.toList()));
    wrapper.getTree().setRightIf(right.stream().map(this::convertWrapper).collect(Collectors.toList()));
    return wrapper.getTree();
  }
}
