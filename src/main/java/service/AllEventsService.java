package service;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDao;
import requestresult.AllEventsResult;

import java.sql.Connection;

/**
 * Processes AllEvents requests and results
 */
public class AllEventsService {
    /** All Events Service
     * @return AllEventsResult Object
     */
    public AllEventsResult getAllEvents() {
        AllEventsResult result = new AllEventsResult(false);
        Database db = new Database();

        try {
            Connection conn = db.getConnection();
            EventDao eDao = new EventDao(conn);
            //TODO: find way to get username (authtoken?)
            //eDao.getEventsForUser("username"); //this Dao action has to implemented
            db.closeConnection(true);
            result.setSuccess(true);
            result.setMessage("Clear succeeded.");
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setMessage("Error: " + e.getMessage());
        }

        return result;
    }
}
