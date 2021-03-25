package edu.austral.ingsis;

public class VariableBuilder {

    private final Variable variable;

    public VariableBuilder() {
        variable = new ConcreteVariable();
    }

    public Variable build() {
        return variable;
    }

    public VariableBuilder setName(String name) {
        variable.setName(name);
        return this;
    }

    public VariableBuilder setType(String type) {
        variable.setType(type);
        return this;
    }
}
