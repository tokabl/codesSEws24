package org.hbrs.se1.ws24.exercises.uebung3.test;


import org.hbrs.se1.ws24.exercises.uebung3.Container;
import org.hbrs.se1.ws24.exercises.uebung3.ContainerException;
import org.hbrs.se1.ws24.exercises.uebung3.Member;
import org.hbrs.se1.ws24.exercises.uebung3.MemberKonkret;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceStrategy;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceStrategyStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ContainerTest {
    private Container container;

    @BeforeEach
    public void setUp() throws PersistenceException {
        container = Container.getInstance();
    }

    // Test 1: Testet, ob die Methode store() die Exception PersistenceException wirft, wenn keine Strategie gesetzt ist.
    @Test
    public void testNoStrategySetOnStore() {
        try {
        container.setPersistenceStrategy(null); // setzt keine Strategie (null)
        container.store();

    } catch (PersistenceException exception) {
            assertEquals("java.lang.Exception: No strategy is set", exception.getMessage());
            assertEquals(exception.getExceptionTypeType(), PersistenceException.ExceptionType.NoStrategyIsSet);
    }
    }

    // Test 2: Testet, ob die Methode load() die Exception PersistenceException wirft, wenn keine Strategie gesetzt ist.
    @Test
    public void testNoStrategySetOnLoad() {
        container.setPersistenceStrategy(null); // setzt keine Strategie (null)
        PersistenceException exception = assertThrows(PersistenceException.class, () -> {
            container.load();
        });
        assertEquals("java.lang.Exception: No strategy is set", exception.getMessage()); // Nachricht der Exception überprüfen
        assertEquals(PersistenceException.ExceptionType.NoStrategyIsSet, exception.getExceptionTypeType()); // Exception-Typ überprüfen
    }


    // Test 3: Verwendung der Strategie "PersistenceStrategyMongoDB"
    @Test
    public void testMongoDBStrategy() {
        try {
            PersistenceStrategyStream<Member> file = new PersistenceStrategyStream<Member>();

            // FileStreams do not like directories, so try this out ;-)
            file.setLocation("C:\\Users\\User\\Desktop\\objects.ser");
            container.setPersistenceStrategy(file);
            container.store();

        } catch (PersistenceException e) {
            System.out.println("Message: " + e.getMessage());
            assertEquals(e.getMessage(), "java.lang.Exception: File not found, connection not available");
            assertEquals(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    e.getExceptionTypeType());
        }
    }

    // Test 4: Fehlerhafte Location des Files für Streams
    @Test
    void testinvalidFileLocation() {
        try {
            PersistenceStrategy<Member> file = new PersistenceStrategyStream<Member>();

            // setzt die Location auf ein Directory
            ((PersistenceStrategyStream<Member>) file).setLocation("C:\\Users\\User\\Desktop\\");
            container.setPersistenceStrategy(file);
            container.store();

        } catch (PersistenceException e) {
            assertEquals(e.getMessage(), "java.lang.Exception: File not found, connection not available");
            assertEquals( e.getExceptionTypeType(), PersistenceException.ExceptionType.ConnectionNotAvailable);
        }

    }

    @Test
    void testRoundtrip() {
        try {
            container.setPersistenceStrategy(new PersistenceStrategyStream<Member>());

            // Objekte in Container speichern
            container.addMember(new MemberKonkret(1));
            assertEquals(1, container.size());
            container.store();


            container.deleteMember(1);
            assertEquals(0, container.size());

            container.load();
            assertEquals(1, container.size());


        } catch (PersistenceException | ContainerException e) {
            System.out.println("Message: " + e.getMessage() );
        }


    }



    }

