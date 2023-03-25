package service;

import dataAccess.*;
import model.Authtoken;
import model.Event;
import model.Person;
import requestresult.OneEventResult;

import java.sql.Connection;

/**
 * Processes OneEvent requests and results
 */
public class OneEventService  {
    /** One Event Service
     * @return OneEventResult Object
     */
    public OneEventResult getEvent(String authtoken, String eventID) {
        OneEventResult result = new OneEventResult(false);
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

            //find event
            EventDao eDao = new EventDao(conn);
            Event event = eDao.find(username, eventID);
            if (event != null) {
                //fill result with event info
                result.setInfo(event.getEventID(), event.getAssociatedUsername(), event.getPersonID(), event.getLatitude(),
                        event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());

                db.closeConnection(true);
                result.setSuccess(true);
            }
            else {
                throw new DataAccessException("Couldn't find Event with provided eventID");
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
