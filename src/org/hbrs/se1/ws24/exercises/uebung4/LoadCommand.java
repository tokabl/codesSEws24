package org.hbrs.se1.ws24.exercises.uebung4;


import org.hbrs.se1.ws24.exercises.uebung4.persistence.PersistenceException;

public class LoadCommand implements Command {
    private Container container;

    public LoadCommand(Container container) {
        this.container = container;
    }

    public void execute(String[] args) {
        try {
            container.load();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {

    }
}
