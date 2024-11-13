package org.hbrs.se1.ws24.exercises.uebung4;

public class ExitCommand implements Command {
    public void execute(String[] args) {
        System.out.println("Das Programm wird beendet");
        System.exit(0);
    }

    @Override
    public void undo() {

    }
}
