package edu.austral.ingsis;

import edu.austral.ingsis.rules.RuleType;
import java.nio.file.Path;
import java.util.ArrayList;
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
    AST tree;
    if (ast.getType().equals(RuleType.IF)) tree = setIfChildren(ast);
    else tree = ast.getTree();
    return new ASTInContext(tree, semanticAnalyzer.getContext());
  }

  private AST setIfChildren(ASTWrapper wrapper) {
    wrapper.getTree().setLeftIf(convertWrapperToAST(wrapper.getLeft()));
    wrapper.getTree().setRightIf(convertWrapperToAST(wrapper.getRight()));
    return wrapper.getTree();
  }

  private List<AST> convertWrapperToAST(List<ASTWrapper> wrappers) {
    List<AST> asts = new ArrayList<>();
    for (ASTWrapper wrap : wrappers) asts.add(wrap.getTree());
    return asts;
  }
}
