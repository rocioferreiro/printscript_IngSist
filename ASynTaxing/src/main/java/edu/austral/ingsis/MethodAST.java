package edu.austral.ingsis;

public class MethodAST implements ASTBranch {

    private Token token;
    private AST leftChild;
    private AST rightChild;

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
    public AST addAST(AST ast) {
        return null;
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
}
