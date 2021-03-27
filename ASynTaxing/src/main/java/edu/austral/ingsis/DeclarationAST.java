package edu.austral.ingsis;

import java.util.Optional;

public class DeclarationAST implements ASTComposite{

    private Token token;
    private Optional<AST> leftChild;
    private Optional<AST> rightChild;

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public Optional<AST> getLeftChild() {
        return leftChild;
    }

    @Override
    public Optional<AST> getRightChild() {
        return rightChild;
    }

    @Override
    public AST addAST(AST ast) {
        return null;
    }
}
