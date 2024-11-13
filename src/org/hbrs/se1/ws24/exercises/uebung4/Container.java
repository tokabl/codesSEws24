package org.hbrs.se1.ws24.exercises.uebung4;
import org.hbrs.se1.ws24.exercises.uebung4.persistence.PersistenceException;
import org.hbrs.se1.ws24.exercises.uebung4.persistence.PersistenceStrategy;

import java.util.*;


public class Container {
    /*
    *  eine Klasse zum Management und die Speicherung von UserStory-Objekten
    */




    /*
     * Interne ArrayList zur Abspeicherung der Objekte
     */
    private List<UserStory> liste = null;


    // Referenz auf das Interface PersistenceStrategy
    private PersistenceStrategy<UserStory> persistenceStrategy ;

    // Konstante, die den Dateinamen der Speicherdatei enthaelt
    private final static String FILENAME = "storage.dat";

    /*
     * statische Klassenvariable zur Speicherung der einzigen Instanz des Containers
     * Singleton Pattern
     */
    private static Container instance = new Container();


    /*
     * Konstruktor der Klasse Container
     * private: Verhindert die Erzeugung von Objekten ausserhalb der Klasse
     * Nur über die statische Methode getInstance() kann ein Objekt erzeugt werden
     */
    Container() {
        liste = new ArrayList<UserStory>();
    }


    /*
     * Statische Methode zur Erzeugung der einzigen Instanz der Klasse Containers
     * Singleton Pattern
     * synchronized: Verhindert, dass zwei Objekte gleichzeitig auf die Methode zugreifen
     * Nur ein Objekt kann auf die Methode zugreifen
     */
    public static synchronized Container getInstance() {
        if (instance == null) {
            instance = new Container();
        }
        return instance;
    }


    /*
     * Methode zum Hinzufuegen einer Story
     * @throws ContainerException
     */
    public void addUserStory(UserStory r) throws ContainerException {
        if (contains(r)) {
            throw new ContainerException("Das User Story mit der ID " + r.getId() + " ist bereits vorhanden!");
        }
        liste.add(r);
    }

    /*
     * Methode zur Ueberpruefung, ob ein Story in der Liste enthalten ist
     *
     */
    private boolean contains(UserStory r) {
        int ID = r.getId();
        for (UserStory rec : liste) {
            if ((rec).getId() == ID) {
                return true;
            }
        }
        return false;


    }


    /*
     * Methode zur Bestimmung der Anzahl der von UserStory-Objekten
     * Aufruf der Methode size() aus List
     *
     */
    public int size() {
        return liste.size();
    }


    /*
     * Methode zur Ausgabe der User Story Objekte.
     */
    public void dump() {
        Collections.sort( this.liste );


        for (UserStory rec : liste) {
            System.out.println(rec.toString());
        }
        liste.stream()
                .filter(userStory -> userStory.getAufwand() > 2)   // Filter für Aufwand > 4
                .filter(userStory -> userStory.getPrio() < 2.0)     // Filter für Prio < 2
                .sorted(Comparator.comparingDouble(UserStory::getPrio)) // Sortierung nach Prio
                .forEach(userStory -> System.out.println(userStory.toString())); // Ausgabe jedes UserStory-Objekts
    }

    /**
     * Interne Methode zur Ermittlung einer User Story
     */
    public UserStory getUserStory(Integer id) {
        for (UserStory rec : liste) {
            if (id == rec.getId()) {
                return rec;
            }
        }
        return null;
    }

    /**
     * Methode zum Speichern der User Story-Objekte
     *
     * @throws PersistenceException
     */
    public void store() throws PersistenceException {
        if (this.persistenceStrategy == null) {
            throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, new Exception("No strategy is set"));
        }

        try {
            // Speichern der Liste
            this.persistenceStrategy.save(this.liste);
            System.out.println("Daten wurden erfolgreich gespeichert.");
        } catch (PersistenceException e) {
            // Genaue Fehlerbeschreibung hinzufügen
            System.out.println("Fehler beim Speichern: " + e.getMessage());
            throw new PersistenceException(PersistenceException.ExceptionType.SaveFailure, new Exception("Error while saving the file", e));
        }
    }


    /**
     * Methode zum Laden der User Story-Objekte
     *
     * @throws PersistenceException
     */
    public void load() throws PersistenceException {
        if (this.persistenceStrategy == null) {
            throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, new Exception("No strategy is set"));
        }

        List<UserStory> loadedList = this.persistenceStrategy.load();

        // Sicherstellen, dass keine doppelten UserStories mit der gleichen ID existieren
        for (UserStory us : loadedList) {
            if (!contains(us)) {
                liste.add(us);
            }
        }
    }


    /**
     * Methode zum Setzen der PersistenceStrategy
     * Strategy Pattern:
     */
    public void setPersistenceStrategy(PersistenceStrategy<UserStory> strategy) {
        this.persistenceStrategy = strategy;
    }


    /**
     * Methode zum Ausgeben der aktuellen Liste
     */
    public List<UserStory> getCurrentList() {
        return this.liste;
    }


    public void clear() {
        liste.clear();
    }


}

