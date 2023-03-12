package service;

import dataAccess.*;
import model.Authtoken;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.AllPersonsResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AllPersonsServiceTest {
    private Database db;
    private User user;
    private Authtoken authtoken;
    private Person person;
    private Person person1;
    private AllPersonsResult result;
    private AllPersonsService service;

    @BeforeEach
    public void setUp() throws DataAccessException {
            db = new Database();
            person = new Person("123ABC", "jameson12", "Jameson",
                    "Jackson", "M", null, null, null);
            person1 = new Person("123DEF", "jameson12", "Steele",
                    "Jackson", "M", null, null, null);
            user = new User("jameson12", "dogs0987654321", "j.jackson@gmail.com",
                    "Jameson", "Jackson", "M", "123ABC");
            authtoken = new Authtoken("token123", "jameson12");
            service = new AllPersonsService();
            db.getConnection();
            db.clearTables();
            new UserDao(db.getConnection()).insert(user);
            new AuthtokenDao(db.getConnection()).insert(authtoken);
            new PersonDao(db.getConnection()).insert(person);
            new PersonDao(db.getConnection()).insert(person1);
            db.closeConnection(true);
    }

    @Test
    public void successFindAllPersons() throws DataAccessException {
        result = service.getAllPersons("token123");
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void failedFindAllPersons() throws DataAccessException {
        result = service.getAllPersons("wrongtoken");
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Invalid authtoken");
        assertFalse(result.isSuccess());
    }
}
