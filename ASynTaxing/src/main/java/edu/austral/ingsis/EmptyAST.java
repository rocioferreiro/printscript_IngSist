package edu.austral.ingsis;

import java.util.Optional;

public class EmptyAST implements AST{

    private Token token;

    @Override
    public Token getToken() {
        return null;
    }

    @Override
    public AST getLeftChild() {
        return new EmptyAST();
    }

    @Override
    public AST getRightChild() {
        return new EmptyAST();
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public AST addAST(AST ast) {
        return ast;
    }

    @Override
    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public void setLeftChild(AST ast) {}

    @Override
    public void setRightChild(AST ast) {}

    @Override
    public boolean isEmpty() {
        return true;
    }
}
