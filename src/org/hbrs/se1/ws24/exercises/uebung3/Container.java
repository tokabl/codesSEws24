package org.hbrs.se1.ws24.exercises.uebung3;

import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.List;


public class Container {


    /*
     * Interne ArrayList zur Abspeicherung der Objekte
     * Alternative: HashMap oder Set. HashMap hat vor allem Probleme
     * bei der Bewahrung der Konsistenz vom Key und Value (siehe TestStore, letzter Test)
     */
    private List<Member> liste = null;


    // Referenz auf das Interface PersistenceStrategy
    private PersistenceStrategy<org.hbrs.se1.ws24.exercises.uebung3.Member> persistenceStrategy;

    // Konstante, die den Dateinamen der Speicherdatei enthaelt
    private final static String FILENAME = "storage.dat";

    /*
     * statische Klassenvariable zur Speicherung der einzigen Instanz des Containers
     * Singleton Pattern
     */
    private static Container instance = null;


    /*
     * Konstruktor der Klasse Container
     * private: Verhindert die Erzeugung von Objekten ausserhalb der Klasse
     * Nur über die statische Methode getInstance() kann ein Objekt erzeugt werden
     */
    private Container() {
        this.liste = new ArrayList<Member>();
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
     * Methode zum Hinzufuegen einer Member.
     * @throws ContainerException
     */
    public void addMember(Member r) throws ContainerException {

        if (r == null) {
            ContainerException ex = new ContainerException();
            throw ex;
        }

        if (contains(r) == true) {
            ContainerException ex = new ContainerException(r.getID().toString());
            throw ex;
        }
        liste.add(r);

    }

    /*
     * Methode zur Ueberpruefung, ob ein Member-Objekt in der Liste enthalten ist
     *
     */
    private boolean contains(Member r) {
        Integer ID = r.getID();
        for (Member rec : liste) {
            // wichtig: Check auf die Values innerhalb der Integer-Objekte!
            if (rec.getID().intValue() == ID.intValue()) {
                return true;
            }
        }
        return false;

        // liste.contains(r), falls equals-Methode in Member ueberschrieben.
    }

    /*
     * Methode zum Loeschen einer Member
     * In Praxis durchaus verwendet: C-Programme; beim HTTP-Protokoll; SAP-Anwendung (R3); Mond-Landung ;-)
     *
     */
    public String deleteMember(Integer id) {
        Member rec = getMember(id);
        if (rec == null) return "Member nicht enthalten - ERROR";
        else {
            liste.remove(rec);
            return "Member mit der ID " + id + " konnte geloescht werden";
        }
    }

    /*
     * Methode zur Bestimmung der Anzahl der von Member-Objekten
     * Aufruf der Methode size() aus List
     *
     */
    public int size() {
        return liste.size();
    }


    /*
     * Methode zur Ausgabe aller IDs der Member-Objekte.
     */
    public void dump() {
        System.out.println("Ausgabe aller Member-Objekte: ");

        // mit For each Schleife: Sequentielle Bearbeitung der Schleife
        for (Member p : liste) {
            System.out.println(p.toString());
        }
    }

    /**
     * Interne Methode zur Ermittlung einer Member
     */
    private Member getMember(Integer id) {
        for (Member rec : liste) {
            if (id == rec.getID().intValue()) {
                return rec;
            }
        }
        return null;
    }

    /**
     * Methode zum Speichern der Member-Objekte persistent in einer Datei (CR2)
     *
     * @throws PersistenceException
     */
    public void store() throws PersistenceException {
        if (this.persistenceStrategy == null) {
            throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, new Exception("No strategy is set"));


        }
        this.persistenceStrategy.save(this.liste);
    }


    /**
     * Methode zum Laden der Member-Objekte aus einer Datei (CR2)
     *
     * @throws PersistenceException
     */
    public void load() throws PersistenceException {
        if (this.persistenceStrategy == null) {
            throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, new Exception("No strategy is set"));
        }
        List<Member> list = this.persistenceStrategy.load();

    }


    /**
     * Methode zum Setzen der PersistenceStrategy
     * Strategy Pattern:
     */
    public void setPersistenceStrategy(PersistenceStrategy<Member> strategy) {
        this.persistenceStrategy = strategy;
    }


    /**
     * Methode zum Ausgeben der aktuellen Liste
     */
    public List<Member> getCurrentList() {
        return this.liste;
    }


}

