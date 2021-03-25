package edu.austral.ingsis;

import java.util.List;

public interface Command {
    Variable execute(List<Token> tokens);
}
