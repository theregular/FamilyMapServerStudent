package service;

import dataAccess.*;
import model.Authtoken;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.OnePersonResult;

import static org.junit.jupiter.api.Assertions.*;

public class OnePersonServiceTest {
    private Database db;
    private User user;
    private Authtoken authtoken;
    private Person person;
    private OnePersonResult result;
    private OnePersonService service;

    @BeforeEach
    public void setUp() throws DataAccessException {
        try {
            db = new Database();
            person = new Person("ID456","jameson12","Jameson",
                    "Jackson","M","321ABC","123CBA",null);
            user = new User("jameson12","dogs0987654321","j.jackson@gmail.com",
                    "Jameson","Jackson", "M", "123ABC");
            authtoken = new Authtoken("token123", "jameson12");
            service = new OnePersonService();
            db.getConnection();
            db.clearTables();
            new UserDao(db.getConnection()).insert(user);
            new AuthtokenDao(db.getConnection()).insert(authtoken);
            new PersonDao(db.getConnection()).insert(person);
            db.closeConnection(true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while setting up for the AllEvents test");
        }
    }
    @Test
    public void SuccessFindOneEvent() throws DataAccessException {
        result = service.getPerson("token123","ID456");
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void FailFindEventAuth() throws DataAccessException {
        result = service.getPerson("token123","wrongID");
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    public void FailFindEventID() throws DataAccessException {
        result = service.getPerson("wrongAuth", "ID456");
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }
}
