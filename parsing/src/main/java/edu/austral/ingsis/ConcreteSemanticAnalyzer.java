package edu.austral.ingsis;

import java.util.List;

public class ConcreteSemanticAnalyzer implements SemanticAnalyzer {

    private Context context;
    private List<RuleTypeListener> listeners;

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
