package edu.austral.ingsis;

import java.util.List;

public class OperationRule implements Rule {
    @Override
    public boolean validateTokens(List<Token> list) {
        return false;
    }
}
