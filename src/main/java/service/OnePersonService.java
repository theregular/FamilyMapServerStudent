package service;

import dataAccess.AuthtokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDao;
import model.Authtoken;
import model.Person;
import requestresult.OnePersonResult;

import java.sql.Connection;

/**
 * Processes OnePerson requests and results
 */
public class OnePersonService {
    /** One Person Service
     * @return OnePersonResult Object
     */
    public OnePersonResult getPerson(String authtoken, String personID) {
        OnePersonResult result = new OnePersonResult(false);
        Database db = new Database();
        try {
            //connect DAO to database
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

            //find person
            PersonDao pDao = new PersonDao(conn);
            Person person = pDao.find(username, personID);
            if (person != null) {
                //System.out.println("Person not null");
                //System.out.println(person);

                //fill result with person info
                result.setInfo(person.getPersonID(), person.getAssociatedUsername(), person.getFirstName(), person.getLastName(), person.getGender());
                //sets fatherID, motherID, spouseID if not null
                if (person.getFatherID() != null) {
                    result.setFatherID(person.getFatherID());
                }
                if (person.getMotherID() != null) {
                    result.setMotherID(person.getMotherID());
                }
                if (person.getSpouseID() != null) {
                    result.setSpouseID(person.getSpouseID());
                }

                db.closeConnection(true);
                result.setSuccess(true);
            }
            else {
                throw new DataAccessException("Invalid personID!");
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
