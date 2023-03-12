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
    private int numUsers;
    private int numPersons;
    private int numEvents;

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

            for (User user : r.getUsers()) {
                uDao.insert(user);
            }

            for (Person person : r.getPersons()) {
                pDao.insert(person);
            }

            for (Event event : r.getEvents()) {
                eDao.insert(event);
            }

            db.closeConnection(true);
            result.setSuccess(true);
            //result.setMessage("Successfully added " + numUsers + "users, "+ numPersons + " persons, and " + numEvents + " events to the database.");
            result.setMessage("Successfully added " + r.getUsers().length + "users, "+ r.getPersons().length + " persons, and " + r.getEvents().length + " events to the database.");
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setMessage("Error: " + e.getMessage());
        }

        return result;
    }

}
