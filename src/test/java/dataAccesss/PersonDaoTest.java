package dataAccesss;

import dataAccess.Database;
import dataAccess.PersonDao;
import dataAccess.DataAccessException;

import model.Person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person bestPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestPerson = new Person("123ABC","jameson12","Jameson",
                "Jackson","M","321ABC","123CBA","456DEF");
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
    public void clearPass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.clear();
        //assertThrows(DataAccessException.class, () -> eDao.find(bestEvent.getEventID()));
        assertNull(pDao.find(bestPerson.getPersonID()));
    }
}
