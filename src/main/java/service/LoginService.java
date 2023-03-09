package service;

import dataAccess.Database;
import requestresult.LoginResult;
import requestresult.LoginRequest;
/**
 * Processes Login requests and results
 */


///Service classes manage database connections
public class LoginService {

    /** Login Service
     * @param r LoginRequest Object
     * @return LoginResult Object
     */
    LoginResult login(LoginRequest r) {
        Database db = new Database();

        return null;
    }

}
