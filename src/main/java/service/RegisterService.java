package service;

import dataAccess.*;
import model.Authtoken;
import model.Person;
import model.User;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * Processes Register requests and results
 */
public class RegisterService {

    /** Register Service
     * @param r RegisterRequest Object
     * @return RegisterResult Object
     */
    public RegisterResult register(RegisterRequest r) {
        RegisterResult result = new RegisterResult(false);
        Database db = new Database();
        try {
            //TODO: address fail cases of typo stuff/missing info
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);
            AuthtokenDao aDao = new AuthtokenDao(conn);
            //PersonDao pDao = new PersonDao(conn);

            if (uDao.find(r.getUsername()) == null) {
                String gender = r.getGender().toLowerCase();
                if (gender.equals("m") || gender.equals("f")) {
                    String personID = UUID.randomUUID().toString();

                    //fill newUser w/ info from request and generated personID
                    User newUser = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(),
                            r.getLastName(), r.getGender().toLowerCase(), personID);
                    String authToken = UUID.randomUUID().toString();
                    //fill result with info and generated authToken
                    result.setInfo(authToken, newUser.getUsername(), personID);

                    //insert User into Database
                    uDao.insert(newUser);

                    //make new Authtoken for User
                    Authtoken token = new Authtoken(authToken, newUser.getUsername());
                    aDao.insert(token); //insert Authtoken into Database //TODO: maybe only allow one authtoken per user?


                    //generate new Data for user -- use FillService?


                    db.closeConnection(true);
                    result.setSuccess(true);
                } else {
                    throw new DataAccessException("Please enter m or f for gender");
                }
            }
            else {
                throw new DataAccessException("Username already in use, please use a different one.");
            }
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setMessage("Error: " + e.getMessage());
        }

        return result;
    }

    private void generateInfo() {
        //TODO: add spot to generate family info here
    }


}
