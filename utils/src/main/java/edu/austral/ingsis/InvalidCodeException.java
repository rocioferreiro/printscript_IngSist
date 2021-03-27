package edu.austral.ingsis;

public class InvalidCodeException extends RuntimeException {

  public InvalidCodeException(String message, Position where) {
    super("\n\n" + message + "\nError begins here: " + where.toString() + "\n\n");
  }
}
