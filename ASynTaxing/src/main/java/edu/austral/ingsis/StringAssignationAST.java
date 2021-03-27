package edu.austral.ingsis;

public class StringAssignationAST implements ASTLeaf{

    private Token token;

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public AST addAST(AST ast) {
        return null;
    }
}
