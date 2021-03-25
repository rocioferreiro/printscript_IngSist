package edu.austral.ingsis;

public class ConcreteVariable implements Variable {

    private String name;
    private String type;

    public ConcreteVariable() {
    }

    public ConcreteVariable(String name, String type) {
        this.name = name;
        this.type = type;
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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

}
