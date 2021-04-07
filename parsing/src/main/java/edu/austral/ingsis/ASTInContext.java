package edu.austral.ingsis;

public class ASTInContext {

    private final AST ast;
    private final Context context;

    public ASTInContext(AST ast, Context context) {
        this.ast = ast;
        this.context = context;
    }

    public AST getAst() {
        return ast;
    }

    public Context getContext() {
        return context;
    }
}
