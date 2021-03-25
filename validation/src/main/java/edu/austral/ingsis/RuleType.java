package edu.austral.ingsis;

import java.util.List;

public enum RuleType {
    DECLARATION(1, (List<Token> tokens)-> new VariableBuilder()
            .setName(tokens.get(1).getValue())
            .setType(tokens.get(3).getValue())
            .build()),
    ASSIGNATION(2, (List<Token> tokens) -> {
        if(tokens.get(0).getType().equals(KeyWord.DECLARATION)){
            //TODO operations
            return new VariableBuilder()
                    .setName(tokens.get(1).getValue())
                    .setType(null)
                    .build();
        } else {
            //TODO without declaration
            return null;
        }
    }),
    PRINT(3, (List<Token> tokens)->null),
    INVALID(0, (List<Token> tokens)->null);

    private final int id;
    private final Command command;

    RuleType(int id, Command command) {
        this.id = id;
        this.command = command;
    }

    public static RuleType ruleOfId(int id){
        for(RuleType type : values()){
            if(type.id == id) return type;
        }
        return INVALID;
    }
}
