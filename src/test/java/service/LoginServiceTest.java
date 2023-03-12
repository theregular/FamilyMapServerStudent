package service;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDao;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.LoginRequest;
import requestresult.LoginResult;

import java.io.FileNotFoundException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private Database db;
    private User bestUser;
    private LoginRequest passRequest;
    private LoginRequest wrongPasswordRequest;
    private LoginRequest wrongIDRequest;
    private LoginRequest emptyUsernameRequest;
    private LoginRequest emptyPasswordRequest;
    private LoginService service;
    private LoginResult result;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("jameson12","dogs0987654321","j.jackson@gmail.com",
                "Jameson","Jackson", "M", "123ABC");
        passRequest = new LoginRequest("jameson12", "dogs0987654321");
        emptyUsernameRequest = new LoginRequest(null, "dogs0987654321");
        emptyPasswordRequest = new LoginRequest("jameson12", null);
        wrongIDRequest = new LoginRequest("wrong", "what");
        wrongPasswordRequest = new LoginRequest("jameson12", "wrongpassword");
        Connection conn = db.getConnection();
        db.clearTables();
        new UserDao(conn).insert(bestUser);
        db.closeConnection(true);
        service = new LoginService();
    }

    @Test
    public void LoginPass() throws DataAccessException {
        result = service.login(passRequest);
        assertNotNull(result);
        assertEquals(result.getUsername(), "jameson12");
        assertEquals(result.getPersonID(), "123ABC");
        assertTrue(result.isSuccess());
    }

    @Test
    public void EmptyRequestField() throws DataAccessException {
        result = service.login(emptyUsernameRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Please provide a valid username and/or password");
        assertFalse(result.isSuccess());
        result = service.login(emptyPasswordRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Please provide a valid username and/or password");
        assertFalse(result.isSuccess());
    }

    @Test
    public void UNnotFound() throws DataAccessException {
        result = service.login(wrongIDRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Username not found, please register!");
        assertFalse(result.isSuccess());
    }

    @Test
    public void IncorrectInfo() throws DataAccessException {
        result = service.login(wrongPasswordRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Incorrect username or password!");
        assertFalse(result.isSuccess());
    }
}