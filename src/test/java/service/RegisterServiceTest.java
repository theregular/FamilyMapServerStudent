package service;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDao;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegisterServiceTest {
    private Database db;
    private User bestUser;
    private User compareUser;
    private RegisterRequest passRequest;
    private RegisterRequest usedUsernameRequest;
    private RegisterRequest emptyFieldRequest;

    private RegisterRequest badGender;
    private RegisterService service;
    private RegisterResult result;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("jameson12","dogs0987654321","j.jackson@gmail.com",
                "Jameson","Jackson", "M", "123ABC");
        passRequest = new RegisterRequest("jameson12", "dogs0987654321", "j.jackson@gmail.com",
                "Jameson", "Jackson", "M");
        usedUsernameRequest = new RegisterRequest("jameson12", "password", "goodjameson@yahoo.com",
                "Jameson", "Jamesony", "M");
        emptyFieldRequest = new RegisterRequest(null, null, null, null, null, null);
        badGender = new RegisterRequest("jameson12", "dogs0987654321", "j.jackson@gmail.com",
                "Jameson", "Jackson", "m/f");
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
        service = new RegisterService();
    }

    @Test
    public void registerPass() throws DataAccessException {
        result = service.register(passRequest);
        assertNotNull(result);
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getPersonID());
        assertEquals(result.getUsername(), "jameson12");
    }

    @Test
    public void EmptyRequestField() throws DataAccessException {
        result = service.register(emptyFieldRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Please fill all request fields");
    }

    @Test
    public void UsedUserName() throws DataAccessException {
        new UserDao(db.getConnection()).insert(bestUser);
        db.closeConnection(true);
        result = service.register(usedUsernameRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Username already in use, please use a different one.");
    }
    @Test
    public void BadGenderRequest() throws DataAccessException {
        result = service.register(badGender);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Please enter m or f for gender");
    }
}

