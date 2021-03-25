package edu.austral.ingsis;

import java.util.List;

public interface Parser {

    Context parse(List<Token> tokens);
}
