package edu.austral.ingsis;

public interface Variable<T> {

    String getName();
    String getType();
    T getValue();

    void setName(String name);
    void setType(String type);
    void setValue(T value);
}
