package service;

import dataAccess.Database;
import dataAccess.DataAccessException;
import dataAccess.UserDao;
import model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.FillResult;

import java.io.FileNotFoundException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FillServiceTest {
    private User bestUser;
    private FillService service;
    private FillResult result;

    Connection conn;

    @BeforeEach
    public void setUp() throws DataAccessException, FileNotFoundException {
        Database db = new Database();
        bestUser = new User("jameson12","dogs0987654321","j.jackson@gmail.com",
                "Jameson","Jackson", "M", "123ABC");
        conn = db.getConnection();
        db.clearTables();
        UserDao uDao = new UserDao(conn);
        uDao.insert(bestUser);
        db.closeConnection(true);
        service = new FillService();
    }

    @Test
    public void normalFillPass() throws DataAccessException {
        result = service.fill(bestUser.getUsername(), 4);
        assertNotNull(result);
        assertEquals("Successfully added 31 persons and 91 events to the database.", result.getMessage());
    }

    @Test
    public void fiveGenerationPass() throws DataAccessException {
        result = service.fill(bestUser.getUsername(), 5);
        assertNotNull(result);
        assertEquals("Successfully added 63 persons and 187 events to the database.", result.getMessage());
    }

    @Test
    public void invalidGenerationNumberSmall() throws DataAccessException {
        result = service.fill(bestUser.getUsername(), -3);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Please enter a valid number of generations");
    }

    @Test
    public void invalidGenerationNumberBig() throws DataAccessException {
        result = service.fill(bestUser.getUsername(), 200);
        assertNotNull(result);
        assertEquals("Error: Please enter a valid number of generations", result.getMessage());
    }
}
