package service;

import dataAccess.AuthtokenDao;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDao;
import model.Authtoken;
import requestresult.AllEventsResult;

import java.sql.Connection;

/**
 * Processes AllEvents requests and results
 */
public class AllEventsService {
    /** All Events Service
     * @return AllEventsResult Object
     */
    public AllEventsResult getAllEvents(String authtoken) {
        AllEventsResult result = new AllEventsResult(false);
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

            //get events from username
            EventDao eDao = new EventDao(conn);
            result.setEvents(eDao.getEventsForUser(username));

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
