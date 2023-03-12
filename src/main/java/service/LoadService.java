package service;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import requestresult.LoadRequest;
import requestresult.LoadResult;

import java.sql.Connection;

/**
 * Processes Load requests and results
 */
public class LoadService {
    private int numUsers = 0;
    private int numPersons = 0;
    private int numEvents = 0;

    /** Load Service
     * @param r LoadRequest Object
     * @return LoadResult Object
     */
    public LoadResult load(LoadRequest r) {
        //TODO: address fail cases of typo stuff/missing info
        LoadResult result = new LoadResult(false);
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            EventDao eDao = new EventDao(conn);

            //check if request is valid
            if (r.getUsers() == null && r.getPersons() == null && r.getEvents() == null) {
                throw new DataAccessException("Empty Request");
            }

            db.clearTables(); //clears DB to prep for data to be added

            //add users
            if (r.getUsers() != null) {
                for (User user : r.getUsers()) {
                    uDao.insert(user);
                    numUsers++;
                }
            }
            //add persons
            if (r.getPersons() != null) {
                for (Person person : r.getPersons()) {
                    pDao.insert(person);
                    numPersons++;
                }
            }

            //add events
            if (r.getEvents() != null) {
                for (Event event : r.getEvents()) {
                    eDao.insert(event);
                    numEvents++;
                }
            }

            db.closeConnection(true);
            result.setSuccess(true);
            result.setMessage("Successfully added " + numUsers + " users, "+ numPersons + " persons, and " + numEvents + " events to the database.");
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setMessage("Error: " + e.getMessage());
        }

        return result;
    }

}
