package org.hbrs.se1.ws24.exercises.uebung4;



import org.hbrs.se1.ws24.exercises.uebung4.persistence.PersistenceException;

import java.util.HashMap;
import java.util.Scanner;

public class CommandHandler {
    private HashMap<String, Command> commands = new HashMap<>();

    public CommandHandler(Container container) {
        commands.put("enter", new EnterCommand(container));
        commands.put("load", new LoadCommand(container));
        commands.put("store", new Command() {
            @Override
            public void execute(String[] args) {
                try {
                    container.store();
                } catch (PersistenceException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void undo() {
                // Optional, falls Undo erforderlich ist
            }
        });
        commands.put("dump", new DumpCommand(container));
        commands.put("exit", new ExitCommand());
        commands.put("help", new HelpCommand());
    }



    public void start() {
        System.out.println("Willkommen");
        System.out.println("Geben Sie help ein, um eine Liste der Befehle zu erhalten");

        Scanner scanner = new Scanner( System.in );


        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String commandName = parts[0];
            String[] args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, parts.length - 1);

            Command command = commands.get(commandName);


            if (command != null) {
                try {
                    command.execute(args);
                } catch (Exception e) {
                    System.out.println("Fehler bei der Befehlsausführung: " + e.getMessage());
                }
            } else {
                System.out.println("Unbekannter Befehl. Bitte 'help' eingeben für eine Liste aller Befehle.");
            }
        }
    }



}


