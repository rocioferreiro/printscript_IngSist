package edu.austral.ingsis;

public enum RuleType {
    DECLARATION(1),
    ASSIGNATION(2),
    PRINT(3),
    INVALID(0);

    private final int id;

    RuleType(int id) {
        this.id = id;
    }

    public static RuleType ruleOfId(int id){
        for(RuleType type : values()){
            if(type.id == id) return type;
        }
        return INVALID;
    }
}
