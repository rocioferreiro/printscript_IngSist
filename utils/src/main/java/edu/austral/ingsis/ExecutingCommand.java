package edu.austral.ingsis;

public class ExecutingCommand {

    public final static ExecutingCommand EMPTY = new ExecutingCommand();

    private Runnable runnable;
    private boolean isActive;

    public ExecutingCommand(Runnable runnable) {
        this.runnable = runnable;
        isActive = true;
    }

    public ExecutingCommand() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void run(){
        if(isActive) runnable.run();
    }
}
