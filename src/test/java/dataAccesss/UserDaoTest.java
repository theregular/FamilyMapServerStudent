package dataAccesss;

import dataAccess.Database;
import dataAccess.UserDao;
import dataAccess.DataAccessException;

import model.Person;
import model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private Database db;
    private User bestUser;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("jameson12","dogs0987654321","j.jackson@gmail.com",
                "Jameson","Jackson", "M", "123ABC");
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }

    @Test
    public void findPass() throws DataAccessException {
        uDao.insert(bestUser);
        User foundUser = uDao.find(bestUser.getUsername());

        assertNotNull(foundUser);
        assertEquals(bestUser, foundUser);
    }

    @Test
    public void findFail() throws DataAccessException {
        uDao.insert(bestUser);

        //assertThrows(DataAccessException.class, () -> eDao.find("Biking_123B"));
        User foundPerson = uDao.find("jameson11");
        assertNotEquals(bestUser,foundPerson);
        assertNull(foundPerson);
    }

    @Test
    public void clearPass() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.clear();
        //assertThrows(DataAccessException.class, () -> eDao.find(bestEvent.getEventID()));
        assertNull(uDao.find(bestUser.getPersonID()));
    }


}
