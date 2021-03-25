package edu.austral.ingsis;

public class ConcreteVariable<T> implements Variable<T> {

    private String name;
    private String type;
    private T value;

    public ConcreteVariable() {
    }

    public ConcreteVariable(String name, String type, T value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public T getValue() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
