package serialisationExercise;

import common.AddressBookDataSource;
import common.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class FileDataSource implements AddressBookDataSource {
    private static final String FILENAME = "addressbook.dat";
    HashMap<String, Person> people;

    public FileDataSource() {
        // If the addressbook file does not exist or is malformed, create a new one
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILENAME))) {
            people = (HashMap<String, Person>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            people = new HashMap<>();
        }
    }

    /**
     * Updates the contents of the addressbook data file
     */
    private void updateFile() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            objectOutputStream.writeObject(people);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPerson(Person p) {
        people.put(p.getName(), p);
        updateFile();
    }

    @Override
    public Person getPerson(String name) {
        return people.get(name);
    }

    @Override
    public int getSize() {
        return people.size();
    }

    @Override
    public void deletePerson(String name) {
        people.remove(name);
        updateFile();
    }

    @Override
    public void close() {
        // Nothing to do
    }

    @Override
    public Set<String> nameSet() {
        return people.keySet();
    }
}
