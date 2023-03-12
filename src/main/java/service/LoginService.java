package service;

import dataAccess.AuthtokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDao;
import model.Authtoken;
import requestresult.LoginResult;
import requestresult.LoginRequest;
import requestresult.RegisterResult;

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
    public LoginResult login(LoginRequest r) { //TODO: add authtoken to database? need to add other stuff to database?
        LoginResult result = new LoginResult(false);
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);
            AuthtokenDao aDao = new AuthtokenDao(conn);

            if (uDao.find(r.getUsername()) != null) { //user registered?
                if (uDao.validate(r.getUsername(), r.getPassword())) { //correct username/password?

                    String personID = uDao.userPersonID(r.getUsername()); //get user's personID
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
