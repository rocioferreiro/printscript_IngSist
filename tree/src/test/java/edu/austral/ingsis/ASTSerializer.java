package edu.austral.ingsis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ASTSerializer {

  private static final Map<String, TokenType> stringToTokenType = new HashMap<>(){{
    put("invalid", TokenType.invalid);
    put("DECLARATION", KeyWord.DECLARATION);
    put("B_ASSIGNATION",KeyWord.B_ASSIGNATION);
    put("N_ASSIGNATION",KeyWord.N_ASSIGNATION);
    put("S_ASSIGNATION",KeyWord.S_ASSIGNATION);
    put("C_DECLARATION",KeyWord.C_DECLARATION);
    put("STRING",KeyWord.STRING);
    put("NUMBER",KeyWord.NUMBER);
    put("BOOLEAN",KeyWord.BOOLEAN);
    put("PRINTLN",KeyWord.PRINTLN);
    put("VARIABLE_REF",KeyWord.VARIABLE_REF);
    put("IF_STATEMENT",KeyWord.IF_STATEMENT);
    put("ELSE_STATEMENT",KeyWord.ELSE_STATEMENT);
    put("EQUAL",Operator.EQUAL);
    put("EQUAL_EQUAL",Operator.EQUAL_EQUAL);
    put("MINOR_EQUAL",Operator.MINOR_EQUAL);
    put("MINOR",Operator.MINOR);
    put("GREATER",Operator.GREATER);
    put("GREATER_EQUAL",Operator.GREATER_EQUAL);
    put("ASTERISK",Operator.ASTERISK);
    put("DASH",Operator.DASH);
    put("HYPHEN",Operator.HYPHEN);
    put("L_KEY",Operator.L_KEY);
    put("R_KEY",Operator.R_KEY);
    put("PLUS",Operator.PLUS);
    put("R_PARENTHESIS",Operator.R_PARENTHESIS);
    put("L_PARENTHESIS",Operator.L_PARENTHESIS);
    put("T_ASSIGNATION",Operator.T_ASSIGNATION);
    put("SEMICOLONS",Operator.SEMICOLONS);
  }};

  public static List<Token> deserializeTokens(File file){
    Scanner scanner = null;
    try {
      scanner = new Scanner(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    List<Token> result = new ArrayList<>();
    assert scanner != null;
    while(scanner.hasNext()){
      String[] values = scanner.nextLine().split(",");
      result.add(new ConcreteToken(stringToTokenType.get(values[0]), values[1], new Position(0,0)));
    }
    return result;
  }

  public static String serializeAst(AST ast){
    if(ast.isLeaf()) return ast.getToken() == null ? "" : ast.getToken().getValue();
    if(!ast.getLeftIf().isEmpty() || !ast.getRightIf().isEmpty()){
      return "{"+ast.getLeftIf().stream().map(ASTSerializer::serializeAst).collect(Collectors.joining(","))+"}"
              + ast.getToken().getValue() +
              "{"+ast.getRightIf().stream().map(ASTSerializer::serializeAst).collect(Collectors.joining(","))+"}";
    }
    return "(" + serializeAst(ast.getLeftChild()) + ", "
            + ast.getToken().getValue()+ ", "
            + serializeAst(ast.getRightChild()) + ")";
  }

  public static String serializeASTWrapper(ASTWrapper wrapper) {
    String ast = serializeAst(wrapper.getTree());
    String type = wrapper.getType().name();
    return "(" + ast + "," + type + ")";
  }
}
