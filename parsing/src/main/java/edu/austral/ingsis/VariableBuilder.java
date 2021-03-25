package edu.austral.ingsis;

public class VariableBuilder<T> {

    private Variable<T> variable;

    public VariableBuilder() {
        variable = new ConcreteVariable<>();
    }

    public Variable<T> build() {
        return variable;
    }

    public VariableBuilder<T> setName(String name) {
        variable.setName(name);
        return this;
    }

    public VariableBuilder<T> setType(String type) {
        variable.setType(type);
        return this;
    }
}
