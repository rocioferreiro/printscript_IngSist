package edu.austral.ingsis.branches;

import edu.austral.ingsis.AST;
import edu.austral.ingsis.ContextBuilder;
import edu.austral.ingsis.EmptyAST;
import edu.austral.ingsis.Token;

public class DashAST implements ASTBranch{

    private Token token;
    private AST leftChild = new EmptyAST();
    private AST rightChild = new EmptyAST();

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public AST getLeftChild() {
        return leftChild;
    }

    @Override
    public AST getRightChild() {
        return rightChild;
    }

    @Override
    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public void setLeftChild(AST ast) {
        leftChild = ast;
    }

    @Override
    public void setRightChild(AST ast) {
        rightChild = ast;
    }

    @Override
    public ContextBuilder executeTree(ContextBuilder context) {
        return null;
    }
}
