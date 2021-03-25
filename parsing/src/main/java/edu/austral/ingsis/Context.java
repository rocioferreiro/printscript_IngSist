package edu.austral.ingsis;

import java.util.ArrayList;
import java.util.List;

public class Context {

    private List<Variable> variables;

    public Context() {
        this.variables = new ArrayList<>();
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
    }

    public boolean checkVariable(Variable variable) {
        for(Variable v: variables) {
            return v.getName().equals(variable.getName());
        }
        return false;
    }

    public boolean checkType(Variable variable) {
        for(Variable v: variables) {
            if(v.getName().equals(variable.getName())) return v.getType().equals(variable.getType());
        }
        return false;
    }
}
