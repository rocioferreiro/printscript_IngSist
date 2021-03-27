package edu.austral.ingsis;

public class Position {
  private final int row;
  private final int column;

  public Position(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public Position incrementRow(int amount) {
    return new Position(row + amount, column);
  }

  public Position incrementColumn(int amount) {
    return new Position(row, column + amount);
  }

  @Override
  public String toString() {
    return "(row: " + row + ", column: " + column + ")";
  }
}
