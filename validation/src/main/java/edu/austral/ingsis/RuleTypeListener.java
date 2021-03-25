package edu.austral.ingsis;

public class RuleTypeListener {

    private final RuleType type;
    private final Command command;

    public RuleTypeListener(RuleType type, Command command) {
        this.type = type;
        this.command = command;
    }
}
