package service;

import dataAccess.*;
import model.Authtoken;
import model.Event;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffresult.EventResult;
import passoffresult.EventsResult;
import requestresult.AllEventsResult;
import requestresult.OneEventResult;
import requestresult.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;

public class AllEventsServiceTest {
    private Database db;
    private User user;
    private Authtoken authtoken;
    private Event event;
    private Event event1;
    private AllEventsResult result;
    private AllEventsService service;

    @BeforeEach
    public void setUp() throws DataAccessException {
            db = new Database();
            event = new Event("Biking_123A", "jameson12", "Gale123A",
                    155.3f, 132.5f, "Japan", "Yongin", "Biking_Around", 2000);
            event1 = new Event("Biking_123B", "jameson12", "Ushiku",
                     133.3f, 115.3f, "Japan", "Yongin", "Biking_Stuff", 2023);
            user = new User("jameson12","dogs0987654321","j.jackson@gmail.com",
                    "Jameson","Jackson", "M", "123ABC");
            authtoken = new Authtoken("token123","jameson12");
            service = new AllEventsService();
            db.getConnection();
            db.clearTables();
            new UserDao(db.getConnection()).insert(user);
            new AuthtokenDao(db.getConnection()).insert(authtoken);
            new EventDao(db.getConnection()).insert(event);
            new EventDao(db.getConnection()).insert(event1);
            db.closeConnection(true);
    }

    @Test
    public void successFindAllEvents() throws DataAccessException {
        result = service.getAllEvents("token123");
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void failedFindAllEvents() throws DataAccessException {
            result = service.getAllEvents("wrongtoken");
            assertNotNull(result);
            assertEquals(result.getMessage(), "Error: Invalid authtoken");
            assertFalse(result.isSuccess());
    }

}
