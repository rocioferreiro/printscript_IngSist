package edu.austral.ingsis;

public class ConcreteSemanticAnalyzer implements SemanticAnalyzer {

    private Context context;

    @Override
    public void analyze(Sentence sentence) {
        updateContext(sentence);
    }

    public Context getContext() {
        return context;
    }

    private void updateContext(Sentence sentence) {

    }
}
