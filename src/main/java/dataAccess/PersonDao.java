package dataAccess;

import model.Person;

import java.sql.*;

public class PersonDao {
    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * Connects to the database when object is instantiated
     * @param conn Connection
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new Person object into the database
     * @param person Person Object
     * @throws DataAccessException
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender, " +
                "FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    /**
     * Returns a Person object that corresponds to the user in the database that has this person ID
     * @param personID String
     * @return Person object
     * @throws DataAccessException
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"), rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * Clears the database of all Persons
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Persons";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }
}
