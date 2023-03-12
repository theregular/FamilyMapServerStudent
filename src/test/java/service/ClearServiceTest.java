package service;


import dataAccess.*;

import model.Authtoken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.ClearResult;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {

    private ClearService service;
    private ClearResult result;
    private User user;
    private Person person;
    private Event event;
    private Authtoken token;
    private Database db;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new ClearService();
        result = new ClearResult(false);
        db = new Database();
        user = new User("jameson12","dogs0987654321","j.jackson@gmail.com",
                "Jameson","Jackson", "M", "123ABC");
        person = new Person("123ABC","jameson12","Jameson",
                "Jackson","M","321ABC","123CBA",null);
        event = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2000);
        token = new Authtoken("token123", "jameson12");
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
        new UserDao(db.getConnection()).insert(user);
        new PersonDao(db.getConnection()).insert(person);
        new EventDao(db.getConnection()).insert(event);
        new AuthtokenDao(db.getConnection()).insert(token);
        db.closeConnection(true);
    }

    @Test
    public void successfulClean() throws DataAccessException {
            result = service.clear();
            assertNotNull(result);
            assertTrue(result.isSuccess());
            assertNull(new UserDao(db.getConnection()).find(user.getUsername()));
            db.closeConnection(true);
            assertNull(new PersonDao(db.getConnection()).find(person.getPersonID()));
            db.closeConnection(true);
            assertNull(new EventDao(db.getConnection()).find(event.getEventID()));
            db.closeConnection(true);
            assertNull(new AuthtokenDao(db.getConnection()).find(token.getAuthToken()));
            db.closeConnection(true);
    }
}
