package org.hbrs.se1.ws24.exercises.uebung4;

public class DumpCommand implements Command {
    private Container container;

    public DumpCommand(Container container) {
        this.container = container;
    }


    @Override
    public void execute(String[] args) {
        container.dump();

    }

    @Override
    public void undo() {

    }
}
