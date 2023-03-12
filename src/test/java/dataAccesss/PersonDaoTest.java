package dataAccesss;

import dataAccess.Database;
import dataAccess.PersonDao;
import dataAccess.DataAccessException;

import model.Event;
import model.Person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person bestPerson;
    private Person bestPerson2;
    private Person bestPerson3;
    private Person[] bestPersons;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestPerson = new Person("123ABC","jameson12","Jameson",
                "Jackson","M","321ABC","123CBA",null);
        bestPerson2 = new Person("123DEF","jameson12","Steele",
                "Jackson","M","123","456","456DEF");
        bestPerson3 = new Person("456ABC","jameson12","Shelley",
                "Jackson","F","789","101112","456DEF");

        bestPersons = new Person[3];
        bestPersons[0] = bestPerson;
        bestPersons[1] = bestPerson2;
        bestPersons[2] = bestPerson3;

        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }
    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }

    @Test
    public void findPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person foundPerson = pDao.find(bestPerson.getPersonID());

        assertNotNull(foundPerson);
        assertEquals(bestPerson, foundPerson);
    }

    @Test
    public void findFail() throws DataAccessException {
        pDao.insert(bestPerson);

        //assertThrows(DataAccessException.class, () -> eDao.find("Biking_123B"));
        Person foundPerson = pDao.find("Biking_123B");
        assertNotEquals(bestPerson,foundPerson);
        assertNull(foundPerson);
    }

    @Test
    public void findwUNPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person foundPerson = pDao.find(bestPerson.getAssociatedUsername(), bestPerson.getPersonID());

        assertNotNull(foundPerson);
        assertEquals(bestPerson, foundPerson);
    }

    @Test
    public void findwUNFail() throws DataAccessException {
        pDao.insert(bestPerson);

        //assertThrows(DataAccessException.class, () -> eDao.find("Biking_123B"));
        Person foundPerson = pDao.find("bungus", bestPerson.getPersonID());
        assertNotEquals(bestPerson,foundPerson);
        assertNull(foundPerson);
    }

    @Test
    public void getPersonsForUserPass() throws DataAccessException {

        for (Person person : bestPersons) {
            pDao.insert(person);
        }
        Person[] personsFound = pDao.getPersonsForUser("jameson12");


        assertNotNull(personsFound);
        for (int i = 0; i < personsFound.length; i++) {
            assertEquals(bestPersons[i],personsFound[i]);
        }
    }

    @Test
    public void getPersonsForUserFail() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.insert(bestPerson2);
        //eDao.insert(bestEvent3);
        /*
        for (Event event : bestEvents) {
            eDao.insert(event);
        }
         */

        Person[] personsFound = pDao.getPersonsForUser("jameson12");

        assertNotNull(personsFound);
        assertNotEquals(bestPersons, personsFound);
    }
    @Test
    public void deletePass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.delete(bestPerson.getAssociatedUsername());

        Person testPerson = pDao.find(bestPerson.getAssociatedUsername(), bestPerson.getPersonID());
        assertNull(testPerson);
    }

    @Test
    public void deleteFail() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.delete("bungus");

        Person testPerson = pDao.find(bestPerson.getAssociatedUsername(), bestPerson.getPersonID());
        assertEquals(bestPerson,testPerson);
    }

    /*
    @Test
    public void getPersonsFromUser() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.delete("bungus");

        Person testPerson = pDao.find(bestPerson.getAssociatedUsername(), bestPerson.getPersonID());
        assertEquals(bestPerson,testPerson);
    }

     */


    @Test
    public void clearPass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.clear();
        //assertThrows(DataAccessException.class, () -> eDao.find(bestEvent.getEventID()));
        assertNull(pDao.find(bestPerson.getPersonID()));
    }
}
