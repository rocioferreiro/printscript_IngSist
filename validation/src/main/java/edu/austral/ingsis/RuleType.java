package edu.austral.ingsis;

import java.util.Arrays;
import java.util.List;

public enum RuleType {
    DECLARATION(1, RuleController::declarationCommand),
    ASSIGNATION(2, RuleController::assignationCommand),
    PRINT(3, RuleController::printCommand),
    INVALID(0, (List<Token> tokens, Context context)->null);

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

    public Command getCommand() {
        return command;
    }

    public void setControllerConstants(String regex){
        List<String> ruleParts = Arrays.asList(regex.split(",").clone());
        if(id == 1){
            RuleController.declarationIndex = ruleParts.indexOf("DECLARATION");
            RuleController.declarationVariableIndex = ruleParts.indexOf("VARIABLE");
            RuleController.declarationTypeIndex = ruleParts.indexOf("DECLARE_TYPE") + 1;
        } if(id == 2){
            RuleController.assignationVariableIndex = ruleParts.indexOf("VARIABLE");
        } else {
            RuleController.methodVariableIndex = ruleParts.indexOf("L_PARENTHESIS") + 1;
        }
    }

}
