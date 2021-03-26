package edu.austral.ingsis;

public class ConcreteSemanticAnalyzer implements SemanticAnalyzer {

    private final Context context;

    public ConcreteSemanticAnalyzer() {
        context = new Context();
    }

    @Override
    public void analyze(Sentence sentence) {
        updateContext(sentence);
    }

    public Context getContext() {
        return context;
    }

    private void updateContext(Sentence sentence) {
        Variable variable = sentence.getType().getCommand().execute(sentence.getTokens(), context);
        if(!variable.getName().isEmpty()){
            if(context.checkVariable(variable)){
                if(!context.checkType(variable)) throw new RuntimeException("Type mismatch! in: " + sentence.getTokens().get(0).getPosition().toString());
            } else {
                context.addVariable(variable);
            }
        }
    }
}
