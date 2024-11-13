package org.hbrs.se1.ws24.exercises.uebung4.persistence;


import java.io.*;
import java.util.List;

public class
PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private FileInputStream fis = null;
    private FileOutputStream fos = null;

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }


    public void openConnection() throws PersistenceException {
        try {
            fos = new FileOutputStream(location);
            fis = new FileInputStream(location);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, new Exception("File not found, connection not available"));
        }
        try {
            oos = new ObjectOutputStream(fos);
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, new Exception("Stream error, connection not available"));
        }
    }


    public void closeConnection() throws PersistenceException {
        try {
            if (oos != null) oos.close();
            if (fos != null) fos.close();
            if (ois != null) ois.close();
            if (fis != null) fis.close();
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, new Exception("Error while closing the connection"));
        }
    }


    @Override
    /**
     * Method for saving a list of objects to a disk (HDD)
     * Look-up in Google for further help! Good source:
     * https://www.digitalocean.com/community/tutorials/objectoutputstream-java-write-object-file
     * (Last Access: Oct, 15th 2024)
     */
    public void save(List<E> userStoryList) throws PersistenceException {
        try {
            openConnection();

            // Speichern der gesamten Liste
            oos.writeObject(userStoryList);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.SaveFailure, new Exception("Error while saving the file"));
        } finally {
            closeConnection();
        }
    }


    @Override
    /**
     * Method for loading a list of objects from a disk (HDD)
     * Some coding examples come for free :-)
     * Take also a look at the import statements above ;-!
     */
    public List<E> load() throws PersistenceException {
        List<E> liste = null;

        // Überprüfen, ob die Datei existiert
        File file = new File(location);
        if (!file.exists()) {
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure, new Exception("File does not exist"));
        }

        try {
            fis = new FileInputStream(location);
            ois = new ObjectInputStream(fis);

            // Lesen der Liste von UserStory-Objekten
            Object obj = ois.readObject();

            // Überprüfen, ob das geladene Objekt eine Liste ist
            if (obj instanceof List<?>) {
                liste = (List<E>) obj;
                System.out.println("Daten wurden erfolgreich geladen: " + liste.size() + " Elemente.");
            } else {
                throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure, new Exception("File does not contain a list"));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Hier die Fehlermeldung ausgeben, um genau zu sehen, was schiefgeht
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure, new Exception("Error while loading the file"));
        } finally {
            closeConnection();
        }

        return liste;
    }

}

