package edu.austral.ingsis;

import edu.austral.ingsis.branches.AssignationAST;
import edu.austral.ingsis.branches.PlusAST;
import edu.austral.ingsis.leaves.LiteralAST;
import edu.austral.ingsis.leaves.VariableAST;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeAnalyzerTest {

  @Test
  public void testASTIsLeaf() {
    Context context = new Context();
    context.addVariable(
            new ConcreteVariable(
                    "x", new VariableType(KeyWord.STRING.getName(), KeyWord.STRING.getOrdinal())));
    context.addVariable(
            new ConcreteVariable(
                    "y", new VariableType(KeyWord.NUMBER.getName(), KeyWord.NUMBER.getOrdinal())));
    ContextBuilder builder = new ContextBuilder(context);
    AST ast = new LiteralAST();
    ast.setToken(new ConcreteToken(KeyWord.NUMBER, "12", new Position(1, 2)));
    int expected = KeyWord.NUMBER.getOrdinal();
    int actual = TypeAnalyzer.getTreeType(ast, builder);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testASTIsNumberInAssignation() {
    Context context = new Context();
    context.addVariable(
            new ConcreteVariable(
                    "x", new VariableType(KeyWord.STRING.getName(), KeyWord.STRING.getOrdinal())));
    context.addVariable(
            new ConcreteVariable(
                    "y", new VariableType(KeyWord.NUMBER.getName(), KeyWord.NUMBER.getOrdinal())));
    ContextBuilder builder = new ContextBuilder(context);
    AST ast = new AssignationAST();
    AST left = new VariableAST();
    AST right = new LiteralAST();
    ast.setToken(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 2)));
    left.setToken(new ConcreteToken(KeyWord.VARIABLE_REF, "y", new Position(1, 1)));
    right.setToken(new ConcreteToken(KeyWord.NUMBER, "12", new Position(1, 3)));
    ast.setLeftChild(left);
    ast.setRightChild(right);
    int expected = KeyWord.NUMBER.getOrdinal();
    int actual = TypeAnalyzer.getTreeType(ast, builder);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testASTIsStringInAssignation() {
    Context context = new Context();
    context.addVariable(
            new ConcreteVariable(
                    "x", new VariableType(KeyWord.STRING.getName(), KeyWord.STRING.getOrdinal())));
    context.addVariable(
            new ConcreteVariable(
                    "y", new VariableType(KeyWord.NUMBER.getName(), KeyWord.NUMBER.getOrdinal())));
    ContextBuilder builder = new ContextBuilder(context);
    AST ast = new AssignationAST();
    AST left = new VariableAST();
    AST right = new LiteralAST();
    ast.setToken(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 2)));
    left.setToken(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 1)));
    right.setToken(new ConcreteToken(KeyWord.STRING, "hola", new Position(1, 3)));
    ast.setLeftChild(left);
    ast.setRightChild(right);
    int expected = KeyWord.STRING.getOrdinal();
    int actual = TypeAnalyzer.getTreeType(ast, builder);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testASTIsStringInOperationOfNumberAndString() {
    Context context = new Context();
    context.addVariable(
            new ConcreteVariable(
                    "x", new VariableType(KeyWord.STRING.getName(), KeyWord.STRING.getOrdinal())));
    context.addVariable(
            new ConcreteVariable(
                    "y", new VariableType(KeyWord.NUMBER.getName(), KeyWord.NUMBER.getOrdinal())));
    ContextBuilder builder = new ContextBuilder(context);
    AST ast = new AssignationAST();
    AST left = new PlusAST();
    AST right = new LiteralAST();
    ast.setToken(new ConcreteToken(Operator.EQUAL, "=", new Position(1, 2)));
    AST left_left = new VariableAST();
    AST left_right = new LiteralAST();
    left.setToken(new ConcreteToken(Operator.PLUS, "+", new Position(1, 4)));
    left_left.setToken(new ConcreteToken(KeyWord.VARIABLE_REF, "x", new Position(1, 1)));
    left_right.setToken(new ConcreteToken(KeyWord.NUMBER, "2", new Position(1, 2)));
    left.setLeftChild(left_left);
    left.setRightChild(left_right);
    right.setToken(new ConcreteToken(KeyWord.STRING, "hola", new Position(1, 5)));
    ast.setLeftChild(left);
    ast.setRightChild(right);
    int expected = KeyWord.STRING.getOrdinal();
    int actual = TypeAnalyzer.getTreeType(ast, builder);
    Assertions.assertEquals(expected, actual);
  }
}
