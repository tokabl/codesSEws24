package org.hbrs.se1.ws24.exercises.uebung4;


public class EnterCommand implements Command {
    private Container container;

    public EnterCommand(Container container) {
        this.container = container;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 9) {
            System.out.println("Fehler: Ungültige Anzahl von Parametern. Erforderlich: 9");
            return;
        }

        try {
            // Parameter aufteilen und in entsprechende Variablen umwandeln
            int id = parseInt(args[0], "ID");
            String titel = args[1];
            String akzeptanzkriterium = args[2];
            int aufwand = parseInt(args[3], "Aufwand");
            int risiko = parseInt(args[4], "Risiko");
            int mehrwert = parseInt(args[5], "Mehrwert");
            int strafe = parseInt(args[6], "Strafe");
            double prio = parseDouble(args[7], "Prio");
            String projekt = args[8];

            // Erstellen der UserStory
            UserStory userStory = new UserStory(id, titel, akzeptanzkriterium, aufwand, risiko, mehrwert, prio, strafe, projekt);

            // Hinzufügen der UserStory zum Container
            container.addUserStory(userStory);

            System.out.println("User Story wurde erfolgreich hinzugefügt.");

        } catch (NumberFormatException e) {
            System.out.println("Fehler: Ungültige Zahl in den Parametern.");
        } catch (ContainerException e) {
            System.out.println("Fehler beim Hinzufügen der UserStory: " + e.getMessage());
        }
    }

    /**
     * Hilfsmethode, um eine Zahl zu parsen (Integer).
     */
    private int parseInt(String arg, String fieldName) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            System.out.println("Fehler: Ungültiger Wert für " + fieldName + ": " + arg);
            throw e;  // Fehler weitergeben
        }
    }

    /**
     * Hilfsmethode, um eine Zahl zu parsen (Double).
     */
    private double parseDouble(String arg, String fieldName) {
        try {
            return Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            System.out.println("Fehler: Ungültiger Wert für " + fieldName + ": " + arg);
            throw e;
        }
    }

    @Override
    public void undo() {

    }
}