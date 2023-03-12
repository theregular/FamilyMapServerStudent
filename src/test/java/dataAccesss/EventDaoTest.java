package dataAccesss;

import dataAccess.Database;
import dataAccess.EventDao;
import dataAccess.DataAccessException;

import model.Event;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDaoTest {
    private Database db;
    private Event bestEvent;
    private Event bestEvent2;
    private Event bestEvent3;
    private Event[] bestEvents;

    private Person bestPerson;
    private Person otherPerson;
    private EventDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();
        // and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2000);
        bestEvent2 = new Event("Biking_123B", "Gale", "Gale123A",
                35.9f, 140.1f, "Provo", "Ushiku",
                "Biking_Stuff", 2020);
        bestEvent3 = new Event("Biking_123C", "Gale", "Gale123A",
                35.9f, 140.1f, "Nepal", "Ushiku",
                "Biking_Up", 2002);

        bestEvents = new Event[3];
        bestEvents[0] = bestEvent;
        bestEvents[1] = bestEvent2;
        bestEvents[2] = bestEvent3;

        bestPerson = new Person("Gale123A","Gale","Jameson",
                "Jackson","M","321ABC","123CBA","456DEF");

        otherPerson = new Person("Gale1234","Gale","Jameson",
                "Jackson","M","321ABC","123CBA","456DEF");

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        eDao = new EventDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        eDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        // Start by inserting an event into the database.
        eDao.insert(bestEvent);
        // Let's use a find method to get the event that we just put in back out.
        Event compareTest = eDao.find(bestEvent.getEventID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestEvent, compareTest);
    }


    @Test
    public void insertFail() throws DataAccessException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        eDao.insert(bestEvent);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> eDao.insert(bestEvent));
    }

    @Test
    public void findPass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event foundEvent = eDao.find(bestEvent.getEventID());

        assertNotNull(foundEvent);
        assertEquals(bestEvent, foundEvent);
    }

    @Test
    public void findFail() throws DataAccessException {
        eDao.insert(bestEvent);

        //assertThrows(DataAccessException.class, () -> eDao.find("Biking_123B"));
        Event testEvent = eDao.find("Biking_123B");
        assertNotEquals(bestEvent,testEvent);
        assertNull(testEvent);
    }

    @Test
    public void findwUNPass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event foundEvent = eDao.find(bestEvent.getAssociatedUsername(), bestEvent.getEventID());

        assertNotNull(foundEvent);
        assertEquals(bestEvent, foundEvent);
    }

    @Test
    public void findwUNFail() throws DataAccessException {
        eDao.insert(bestEvent);

        //assertThrows(DataAccessException.class, () -> eDao.find("Biking_123B"));
        Event foundEvent = eDao.find("bungus", bestEvent.getEventID());
        assertNotEquals(bestEvent,foundEvent);
    }

    @Test
    public void getEventsForUserPass() throws DataAccessException {
        //eDao.insert(bestEvent);
        //eDao.insert(bestEvent2);
        //eDao.insert(bestEvent3);
        for (Event event : bestEvents) {
            eDao.insert(event);
        }
        Event[] eventsFound = eDao.getEventsForUser("Gale");

        assertNotNull(eventsFound);
        for (int i = 0; i < eventsFound.length; i++) {
            assertEquals(bestEvents[i],eventsFound[i]);
        }
    }

    @Test
    public void getEventsForUserFail() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.insert(bestEvent2);
        //eDao.insert(bestEvent3);
        /*
        for (Event event : bestEvents) {
            eDao.insert(event);
        }
         */

        Event[] eventsFound = eDao.getEventsForUser("Gale");

        assertNotNull(eventsFound);
        assertNotEquals(bestEvents, eventsFound);
    }

    @Test
    public void getEventsForPersonPass() throws DataAccessException {
        //eDao.insert(bestEvent);
        //eDao.insert(bestEvent2);
        //eDao.insert(bestEvent3);
        for (Event event : bestEvents) {
            eDao.insert(event);
        }
        ArrayList<Event> eventsFound = eDao.getEventsForPerson("Gale123A");

        assertNotNull(eventsFound);
        for (int i = 0; i < eventsFound.size(); i++) {
            assertEquals(bestEvents[i],eventsFound.get(i));
        }
    }

    @Test
    public void getEventsForPersonFail() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.insert(bestEvent2);
        //eDao.insert(bestEvent3);
        /*
        for (Event event : bestEvents) {
            eDao.insert(event);
        }
         */

        ArrayList<Event> eventsFound = eDao.getEventsForPerson("Gale123A");

        Event[] eventsFoundArr = new Event[eventsFound.size()];
        eventsFoundArr = eventsFound.toArray(eventsFoundArr);

        assertNotNull(eventsFound);
        assertNotEquals(bestEvents, eventsFoundArr);
    }

    @Test
    public void findOldestEventYearForPersonPass() throws DataAccessException {
        for (Event event : bestEvents) {
            eDao.insert(event);
        }

        int oldestEventYear = bestEvent2.getYear();

        int oldestEventYearFound = eDao.findOldestEventYearForPerson("Gale123A");

        assertEquals(oldestEventYear, oldestEventYearFound);
    }

    @Test
    public void findOldestEventYearForPersonFail() throws DataAccessException {
        for (Event event : bestEvents) {
            eDao.insert(event);
        }

        int oldestEventYear = bestEvent3.getYear(); //2002

        int oldestEventYearFound = eDao.findOldestEventYearForPerson("Gale123A"); //should be 2020

        assertFalse(oldestEventYear > oldestEventYearFound);
    }

    @Test
    public void deletePass() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.delete(bestPerson);

        Event testEvent = eDao.find(bestEvent.getEventID());
        assertNull(testEvent);
    }

    @Test
    public void deleteFail() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.delete(otherPerson);

        Event testEvent = eDao.find(bestEvent.getEventID());
        assertEquals(bestEvent,testEvent);
    }

    @Test
    public void clearPass() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.clear();
        //assertThrows(DataAccessException.class, () -> eDao.find(bestEvent.getEventID()));
        assertNull(eDao.find(bestEvent.getEventID()));
    }
}
