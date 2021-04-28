package networkExercise;

import common.AddressBookDataSource;
import common.Person;

import java.util.Set;

public class NetworkDataSource implements AddressBookDataSource {
    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 10000;

    @Override
    public void addPerson(Person p) {
        // IMPLEMENT ME
    }

    @Override
    public Person getPerson(String name) {
        // IMPLEMENT ME
        return null;
    }

    @Override
    public int getSize() {
        // IMPLEMENT ME
        return 0;
    }

    @Override
    public void deletePerson(String name) {
        // IMPLEMENT ME
    }

    @Override
    public void close() {
        // IMPLEMENT ME
    }

    @Override
    public Set<String> nameSet() {
        // IMPLEMENT ME
        return null;
    }
}
