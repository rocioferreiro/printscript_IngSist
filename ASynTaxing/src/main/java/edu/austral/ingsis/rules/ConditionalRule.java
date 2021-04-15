package edu.austral.ingsis.rules;

import edu.austral.ingsis.*;
import edu.austral.ingsis.branches.IfAST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConditionalRule implements Rule{

    private final String acceptingRegex;
    private final RuleType type;
    private static final int conditionPosition = 2;
    private final List<Rule> contextApprovedRules;

    public ConditionalRule(String acceptingRegex, RuleType type, List<Rule> contextApprovedRules) {
        this.acceptingRegex = acceptingRegex;
        this.type = type;
        this.contextApprovedRules = contextApprovedRules;
    }

    @Override
    public Optional<ASTWrapper> validateTokens(List<Token> list) {
        String concat =
                list.stream().map(t -> t.getType().getCategory()).collect(Collectors.joining(","));
        if (concat.matches(acceptingRegex)) {
            AST ifAst = new IfAST(list.get(conditionPosition));
            List<AST> ifAsts = getAsts(getBetweenKeysOfIf(list));
            List<AST> elseAsts = getAsts(getBetweenKeysOfElse(list));

//            Collections.reverse(list);
//            AST aux = TokenToASTConverter.convert(list.get(0));
//            for (int i = 1; i < list.size(); i++) {
//                aux = TokenToASTConverter.convert(list.get(i)).addAST(aux);
//            }
//            return Optional.of(new ASTWrapper(aux, type));
        }
        return Optional.empty();
    }

    private List<AST> getAsts(List<Token> tokens){
        List<AST> resultList = new ArrayList<>();
        int amount = TokenCleanUp.getAmountOfSentences(tokens);
        List<Token> sublist = tokens;
        for (int i = 1; i <= amount; i++) {
            int nextIndex = TokenCleanUp.getIndexOfNextSeparator(sublist);
            for (Rule rule : contextApprovedRules) {
                Optional<ASTWrapper> result = rule.validateTokens(sublist);
                result.ifPresent(astWrapper -> resultList.add(astWrapper.getTree()));
            }
            sublist = new ArrayList<>(sublist.subList(nextIndex + 1, sublist.size()));
        }
        return resultList;
    }

    private List<Token> getBetweenKeysOfIf(List<Token> tokens){
        int inicialIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getType().equals(Operator.L_KEY)){
                inicialIndex = i+1;
                endIndex = getEndIndex(tokens, inicialIndex);
                break;
            }
        }
        return tokens.subList(inicialIndex, endIndex);
    }

    private List<Token> getBetweenKeysOfElse(List<Token> tokens){
        int inicialIndex = 0, endIndex = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).getType().equals(KeyWord.ELSE_STATEMENT)){
                inicialIndex = i+2;
                endIndex = getEndIndex(tokens, inicialIndex);
                break;
            }
        }
        return tokens.subList(inicialIndex, endIndex);
    }

    private int getEndIndex(List<Token> tokens, int inicialIndex) {
        int leftKeyCounter = 0;
        for (int j = inicialIndex; j < tokens.size(); j++) {
            if(tokens.get(j).getType().equals(Operator.L_KEY)) leftKeyCounter++;
            if(tokens.get(j).getType().equals(Operator.R_KEY)){
                if(leftKeyCounter > 0) leftKeyCounter--;
                else return j;
            }
        }
        return 0;
    }

    @Override
    public RuleType getRuleType() {
        return null;
    }

    @Override
    public String getAcceptingRegex() {
        return null;
    }
}
