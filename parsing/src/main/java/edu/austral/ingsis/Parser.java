package edu.austral.ingsis;

import java.util.List;

public interface Parser { ASTInContext parse(List<Token> tokens);
}
