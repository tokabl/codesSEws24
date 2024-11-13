package org.hbrs.se1.ws24.exercises.uebung4;

public interface Command {
    public void execute(String[] args);
    public void undo();
}
