package edu.austral.ingsis;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConcreteParser implements Parser {

    private final SyntacticAnalyzer syntacticAnalyzer;
    private final SemanticAnalyzer semanticAnalyzer;

    public ConcreteParser() {
        this.syntacticAnalyzer = new ConcreteSyntacticAnalyzer(Paths.get("rules.txt"));
        this.semanticAnalyzer = new ConcreteSemanticAnalyzer();
    }

    @Override
    public Context parse(List<Token> tokens) {
        checkLastToken(tokens);
        List<Token> sublist = tokens;
        List<AST> result = new ArrayList<>();
        while (!sublist.isEmpty()){
            int nextIndex = getIndexOfNextSemicolon(sublist);
            Sentence sentence = syntacticAnalyzer.analyze(sublist.subList(0, nextIndex));
            sublist = sublist.subList(nextIndex+1, sublist.size());
            semanticAnalyzer.analyze(sentence);
        }
        return semanticAnalyzer.getContext();
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
