package edu.austral.ingsis;

import java.util.List;

public class Line {

    private final String text;
    private final int row;

    public Line(String text, int row) {
        this.text = text;
        this.row = row;
    }

    public String getText() {
        return text;
    }

    public int getRow() {
        return row;
    }

    public Line concatText(String toAdd){
        return new Line(text+toAdd, row);
    }

    @Override
    public String toString(){
        return row + ": " + text;
    }
}
