package edu.austral.ingsis;

public interface SemanticAnalyzer {

    void analyze(Sentence sentence);
    Context getContext();
}
