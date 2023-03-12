package dataAccesss;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.AuthtokenDao;
import model.Authtoken;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthtokenDaoTest {
    private Database db;
    private Authtoken token;
    private AuthtokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        token = new Authtoken("dogs0987654321","jameson12");
        Connection conn = db.getConnection();
        aDao = new AuthtokenDao(conn);
    }
    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        aDao.insert(token);
        Authtoken compareTest = aDao.find(token.getAuthToken());
        assertNotNull(compareTest);
        assertEquals(token, compareTest);
    }


    @Test
    public void insertFail() throws DataAccessException {
        aDao.insert(token);
        assertThrows(DataAccessException.class, () -> aDao.insert(token));
    }

    @Test
    public void findPass() throws DataAccessException {
        aDao.insert(token);
        Authtoken foundToken = aDao.find(token.getAuthToken());

        assertNotNull(foundToken);
        assertEquals(token, foundToken);
    }

    @Test
    public void findFail() throws DataAccessException {
        aDao.insert(token);

        //assertThrows(DataAccessException.class, () -> eDao.find("Biking_123B"));
        Authtoken foundToken = aDao.find("jameson11");
        assertNotEquals(token,foundToken);
        assertNull(foundToken);
    }

    @Test
    public void clearPass() throws DataAccessException {
        aDao.insert(token);
        aDao.clear();
        //assertThrows(DataAccessException.class, () -> eDao.find(bestEvent.getEventID()));
        assertNull(aDao.find(token.getAuthToken()));
    }



}
