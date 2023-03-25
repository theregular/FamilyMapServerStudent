package service;

import dataAccess.AuthtokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDao;
import model.Authtoken;
import model.User;
import requestresult.LoginResult;
import requestresult.LoginRequest;
import requestresult.RegisterResult;

import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;

/**
 * Processes Login requests and results
 */


///Service classes manage database connections
public class LoginService {

    /** Login Service
     * @param r LoginRequest Object
     * @return LoginResult Object
     */

    //TODO: address fail cases of typo stuff/missing info
    public LoginResult login(LoginRequest r) {
        LoginResult result = new LoginResult(false);
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);
            AuthtokenDao aDao = new AuthtokenDao(conn);

            if (r.getUsername() == null || r.getPassword() == null) { //check for request fields to be filled
                throw new DataAccessException("Please provide a valid username and/or password");
            }

            User user = uDao.find(r.getUsername());
            if (user != null) { //user registered?
                if (uDao.validate(r.getUsername(), r.getPassword())) { //correct username/password?

                    String personID = user.getPersonID(); //get user's personID
                    String authToken = UUID.randomUUID().toString(); //generate authtoken
                    result.setInfo(authToken, r.getUsername(), personID);//fill result with info

                    Authtoken token = new Authtoken(authToken, r.getUsername()); //make new authtoken
                    aDao.insert(token);//insert authtoken into DB TODO: maybe only allow one authtoken per user?

                    db.closeConnection(true);
                    result.setSuccess(true);
                }
                else {
                    throw new DataAccessException("Incorrect username or password!");
                }
            }
            else {
                throw new DataAccessException("Username not found, please register!");
            }
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setMessage("Error: " + e.getMessage());
        }

        return result;
    }

}
