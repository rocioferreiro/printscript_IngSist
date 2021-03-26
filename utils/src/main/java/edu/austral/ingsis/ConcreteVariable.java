package edu.austral.ingsis;

public class ConcreteVariable implements Variable {

    private String name;
    private VariableType type;

    public ConcreteVariable() {
        name = "";
    }

    public ConcreteVariable(String name, VariableType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public VariableType getType() {
        return type;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setType(VariableType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "(" + name + ", " + type.getName() + ")";
    }
}
