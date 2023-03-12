package service;

import dataAccess.*;
import model.Authtoken;
import model.Person;
import requestresult.AllPersonsResult;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Processes AllPersons requests and results
 */
public class AllPersonsService {
    /** All Persons Service
     * @return AllPersonsResult Object
     */
    public AllPersonsResult getAllPersons(String authtoken) {
        AllPersonsResult result = new AllPersonsResult(false);
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            //get username from authtoken
            AuthtokenDao aDao = new AuthtokenDao(conn);
            Authtoken token = aDao.find(authtoken);
            if (token == null) {
                throw new DataAccessException("Invalid authtoken");
            }
            String username = token.getUsername();
            //System.out.println(authtoken);
            //System.out.println(username);

            //get persons from username
            PersonDao pDao = new PersonDao(conn);
            result.setPersons(pDao.getPersonsForUser(username));

            db.closeConnection(true);
            result.setSuccess(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setMessage("Error: " + e.getMessage());
        }

        return result;
    }
}
