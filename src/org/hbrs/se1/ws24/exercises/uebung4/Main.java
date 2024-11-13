package org.hbrs.se1.ws24.exercises.uebung4;


import org.hbrs.se1.ws24.exercises.uebung4.persistence.PersistenceStrategy;
import org.hbrs.se1.ws24.exercises.uebung4.persistence.PersistenceStrategyMongoDB;
import org.hbrs.se1.ws24.exercises.uebung4.persistence.PersistenceStrategyStream;

public class Main {
    public static void main(String[] args) {

        Container container = Container.getInstance();
        container.setPersistenceStrategy(new PersistenceStrategyStream<>());
        CommandHandler commandHandler = new CommandHandler(container);
        commandHandler.start();
    }
}
