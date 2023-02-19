package dataAccess;

import model.Event;
import model.Person;
import model.User;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class EventDao {

    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * Connects to the database when object is instantiated
     * @param conn Connection
     */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new Event object into the database
     * @param event Event Object
     * @throws DataAccessException
     */

    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Returns an event object that corresponds to the event in the database that has this event ID
     * @param eventID String
     * @return Event object
     * @throws DataAccessException
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    /**
     * Returns a list of all events associated with a username
     * @param username String
     * @return List<Event>
     * @throws DataAccessException
     */
    //TODO:IMPLEMENT THIS
    public List<Event> getEventsForUser(String username) throws DataAccessException {
        List<Event> events = new ArrayList<>();

        return events;
    }

    /**
     * Deletes all events associated with the person object
     * @param person Person object
     * @throws DataAccessException
     */
    //TODO: TEST THIS
    public void Delete(Person person) throws DataAccessException {
        String sql = "DELETE FROM Events WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while deleting person");
        }
    }

    /**
     * Clears the database of all Events
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Events";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }
}
