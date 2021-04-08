package edu.austral.ingsis;

public class ExecutingCommand {

  public static final ExecutingCommand EMPTY = new ExecutingCommand();

  private Runnable runnable;
  private final boolean isActive;

  public ExecutingCommand(Runnable runnable) {
    this.runnable = runnable;
    isActive = true;
  }

  public ExecutingCommand() {
    isActive = false;
  }

  public void run() {
    if (isActive) runnable.run();
  }
}
