package edu.austral.ingsis;

import java.util.List;

public interface Parser {

    AST parse(List<Token> tokens);
}
