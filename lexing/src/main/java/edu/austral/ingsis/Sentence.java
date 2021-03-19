package edu.austral.ingsis;

import java.util.List;

public class Sentence {

    private final List<Token> tokens;
    private final int firstLine;

    public Sentence(List<Token> tokens, int firstLine) {
        this.tokens = tokens;
        this.firstLine = firstLine;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public int getFirstLine() {
        return firstLine;
    }
}
