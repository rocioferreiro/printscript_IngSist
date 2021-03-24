package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public class ConcreteParser implements Parser {

    private SyntacticAnalyzer syntacticAnalyzer;
    private SemanticAnalyzer semanticAnalyzer;

    @Override
    public List<AST> parse(List<Token> tokens) {
        checkLastToken(tokens);
        List<Token> sublist = tokens;
        List<AST> result = new ArrayList<>();
        while (!sublist.isEmpty()){
            int nextIndex = getIndexOfNextSemicolon(sublist);
            AST tree = syntacticAnalyzer.analyze(sublist.subList(0, nextIndex));
            sublist = sublist.subList(nextIndex+1, sublist.size());
            result.add(semanticAnalyzer.analyze(tree));
        }
        return result;
    }

    private void checkLastToken(List<Token> tokens){
        Token lastToken = tokens.get(tokens.size()-1);
        if(!lastToken.getType().equals(Operator.SEMICOLONS)){
            throw new RuntimeException("Missing last semicolon. In line: " + lastToken.getPosition().getRow());
        }
    }

    private int getIndexOfNextSemicolon(List<Token> tokens){
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getType().equals(Operator.SEMICOLONS)){
                return i;
            }
        }
        return -1;
    }
}
