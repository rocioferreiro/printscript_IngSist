package edu.austral.ingsis;

import java.util.List;

public interface Parser {

    List<AST> parse(List<Token> tokens);
}
