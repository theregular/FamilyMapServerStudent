package dataAccess;

import model.Authtoken;
import model.Person;

import java.sql.*;

public class AuthtokenDao {
    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * Connects to the database when object is instantiated
     * @param conn Connection
     */
    public AuthtokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new authtoken and username into the database
     * @param authtoken String
     * @param username String
     * @throws DataAccessException
     */

    //need authToken to be separate object? Maybe not, maybe just two strings?
    public void insert(String authtoken, String username) throws DataAccessException {
        String sql = "INSERT INTO Authtokens (AuthToken, Username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            stmt.setString(2, username);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authtoken into the database");
        }
    }

    /**
     * Returns the username that corresponds to the user in the database that has this authtoken
     * @param authtoken String
     * @return String username
     * @throws DataAccessException
     */
    public String find(String authtoken) throws DataAccessException {
        String username;
        ResultSet rs;
        String sql = "SELECT * FROM Authtokens WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                username = rs.getString("Username");
                return username;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database");
        }
    }

    /**
     * Clears the database of all Authtokens
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtokens";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authtoken table");
        }
    }
}
