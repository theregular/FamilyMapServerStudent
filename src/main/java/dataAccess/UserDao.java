package dataAccess;

import model.User;
import model.Event;
import model.Person;

import java.sql.*;
/**
 * User DAO Object used to alter information related to users in the database
 */
public class UserDao {
    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * Connects to the database when object is instantiated
     * @param conn Connection
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new User object into the database
     * @param user User Object
     * @throws DataAccessException
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO Users (Username, Password, Email, FirstName, LastName, " +
                "Gender, PersonID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    /**
     * Returns a User object that corresponds to the user in the database that has this username
     * @param username String
     * @return User object
     * @throws DataAccessException
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM Users WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Email"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"), rs.getString("PersonID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    /**
     * Validates username and password association for user logging in
     * @param username String
     * @param password String
     * @return boolean
     * @throws DataAccessException
     */
    //TODO: TEST THIS
    public boolean validate(String username, String password) throws DataAccessException {
        String foundPassword;
        ResultSet rs;
        String sql = "SELECT * FROM Users WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                foundPassword = rs.getString("Password");
            } else {
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while validating username and password in the database");
        }
        return password.equals(foundPassword);
    }

    /*
    public String userPersonID(String username) throws DataAccessException {
        String personID;
        ResultSet rs;
        String sql = "SELECT * FROM Users WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                personID = rs.getString("PersonID");
            } else {
                return null;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while validating username and password in the database");
        }
        return personID;
    }

     */

    /**
     * Clears the database of all Users
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Users";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

}
