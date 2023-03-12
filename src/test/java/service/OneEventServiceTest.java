package service;

import dataAccess.*;
import model.Authtoken;
import model.Event;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.AllEventsResult;
import requestresult.OneEventResult;

import static org.junit.jupiter.api.Assertions.*;

public class OneEventServiceTest {
    private Database db;
    private User user;
    private Authtoken authtoken;
    private Event event;
    private OneEventResult result;
    private OneEventService service;

    @BeforeEach
    public void setUp() throws DataAccessException {
            db = new Database();
            event = new Event("Biking_123A", "jameson12", "123ABC",
                    35.9f, 140.1f, "Japan", "Ushiku",
                    "Biking_Around", 2000);
            user = new User("jameson12","dogs0987654321","j.jackson@gmail.com",
                    "Jameson","Jackson", "M", "123ABC");
            authtoken = new Authtoken("token123","jameson12");
            service = new OneEventService();
            db.getConnection();
            db.clearTables();
            new UserDao(db.getConnection()).insert(user);
            new AuthtokenDao(db.getConnection()).insert(authtoken);
            new EventDao(db.getConnection()).insert(event);
            db.closeConnection(true);
    }
    @Test
    public void successFindOneEvent() throws DataAccessException {
        result = service.getEvent("token123","Biking_123A");
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void failedFindEventAuth() throws DataAccessException {
        result = service.getEvent("token123","wrongID");
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    public void failedFindEventID() throws DataAccessException {
        result = service.getEvent("wrongAuth", "ID456");
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }
}
