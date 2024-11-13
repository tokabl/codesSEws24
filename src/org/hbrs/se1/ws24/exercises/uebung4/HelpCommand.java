package org.hbrs.se1.ws24.exercises.uebung4;

public class HelpCommand implements Command {
    public void execute(String[] args) {
        System.out.println("Folgende Befehle sind verfügbar: ");
        System.out.println("help - Zeigt diese Hilfe an");
        System.out.println("exit - Verlassen der Anwendung");
        System.out.println("enter - Fügt eine neue User Story hinzu");
        System.out.println("dump -sortierte Ausgabe der");
        System.out.println("load - Lädt User Stories von einem Datenträger in ein ContainerObjekt");

    }

    @Override
    public void undo() {

    }
}
