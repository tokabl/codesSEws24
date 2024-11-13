package org.hbrs.se1.ws24.exercises.uebung4.Test;

import org.hbrs.se1.ws24.exercises.uebung4.Container;
import org.hbrs.se1.ws24.exercises.uebung4.ContainerException;
import org.hbrs.se1.ws24.exercises.uebung4.EnterCommand;
import org.hbrs.se1.ws24.exercises.uebung4.UserStory;
import org.hbrs.se1.ws24.exercises.uebung4.persistence.PersistenceException;
import org.hbrs.se1.ws24.exercises.uebung4.persistence.PersistenceStrategyStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserStoryTest {
    private Container container;
    private EnterCommand enterCommand;

    @BeforeEach
    public void setUp() {
        container = Container.getInstance();
        container.clear();
        enterCommand = new EnterCommand(container);
        container.setPersistenceStrategy(new PersistenceStrategyStream<>());

    }

// Test: Ungültige Anzahl an Argumenten
@Test
public void testExecute_InvalidNumberOfArguments() {
    String[] args = {"1", "Test Titel", "Test Akzeptanz", "5", "2", "3", "1", }; // Nur 7 Argumente

    enterCommand.execute(args);

    assertEquals(0, container.size(), "Es sollte keine UserStory hinzugefügt worden sein");
}

// Test: Ungültiges Zahlenformat für Priorität
@Test
public void testExecute_InvalidNumberFormat() {
    String[] args = {"1", "Test Titel", "Test Akzeptanz", "5", "2", "3", "1", "not_a_number", "Projekt"};  // Ungültiges Format für Prio

    enterCommand.execute(args);

    assertEquals(0, container.size(), "Keine UserStory sollte hinzugefügt worden sein");
}

// Test: Erfolgreiches Hinzufügen einer UserStory
@Test
public void testExecute_AddStorySuccessfully() {
    String[] args = {"1", "Titel der User Story", "Akzeptanzkriterium", "5", "2", "3", "1", "1.5", "Projektname"};

    enterCommand.execute(args);

    assertEquals(1, container.size(), "Die Größe des Containers sollte 1 sein.");

    // Abrufen der hinzugefügten UserStory ohne getUserStoryById
    UserStory userStory = container.getUserStory(1);

    assertNotNull(userStory, "Die User Story sollte nicht null sein.");
    assertEquals(1, userStory.getId(), "Die ID der User Story sollte 1 sein.");
    assertEquals("Titel der User Story", userStory.getTitel());
    assertEquals("Akzeptanzkriterium", userStory.getAkzeptanzkriterium());
    assertEquals(5, userStory.getAufwand());
    assertEquals(2, userStory.getRisiko());
    assertEquals(3, userStory.getMehrwert());
    assertEquals(1, userStory.getStrafe());
    assertEquals(1.5, userStory.getPrio(), 0.01);
    assertEquals("Projektname", userStory.getProjekt());
}

// Test: ContainerException bei doppelter ID
@Test
public void testExecute_ContainerException() {
    try {
        container.addUserStory(new UserStory(1, "Test Titel", "Test Akzeptanz", 5, 2, 3, 1.5, 1, "Projekt"));
        String[] args = {"1", "Neue User Story", "Akzeptanz", "5", "2", "3", "1", "1.5", "Projekt"};  // Doppelte ID
        enterCommand.execute(args);
        assertEquals(1, container.size(), "Die Größe des Containers sollte 1 sein.");
    } catch (ContainerException e) {
        fail("Vorbereitung des Tests schlug fehl: " + e.getMessage());
    }
}


// Test: Ungültige Priorität
@Test
public void testExecute_InvalidPrioFormat() {
    String[] args = {"1", "Test Titel", "Test Akzeptanz", "5", "2", "3", "1", "invalid_prio", "Projekt"};  // Ungültiger Wert für Prio

    enterCommand.execute(args);
    assertEquals(0, container.size(), "Es sollte keine UserStory mit ungültiger Priorität hinzugefügt worden sein");
}


// Test: Ungültiger Wert für Titel
@Test
    public void testExecute_InvalidTitle() {
        String[] args = {"1", null, "Test Akzeptanz", "5", "2", "3", "1", "1.5", "Projekt"};  // Ungültiger Wert für Titel

        // Prüfen, ob eine IllegalArgumentException mit der entsprechenden Nachricht geworfen wird

        assertThrows(IllegalArgumentException.class, () -> enterCommand.execute(args), "Ein ungültiger Titel sollte eine IllegalArgumentException werfen");

        // Keine UserStory sollte hinzugefügt worden sein
        assertEquals(0, container.size(), "Es sollte keine UserStory mit ungültigem Titel hinzugefügt worden sein");
    }



// Test: Speichern und Laden
@Test
public void testSaveAndLoad() throws PersistenceException {
    String[] args = {"1", "Titel der User Story", "Akzeptanzkriterium", "5", "2", "3", "1", "1.5", "Projektname"};
    enterCommand.execute(args);

    container.store();
    Container newContainer = Container.getInstance(); // Neuen Container erstellen
    newContainer.load();

    assertEquals(1, newContainer.size(), "Nach dem Laden sollte der Container 1 UserStory enthalten.");
}
}