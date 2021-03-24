package edu.austral.ingsis;

import java.util.List;

public class DeclarationRule implements Rule {
    @Override
    public boolean validateTokens(List<Token> list) {
        return false;
    }
}
