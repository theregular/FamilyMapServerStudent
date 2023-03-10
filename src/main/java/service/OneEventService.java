package service;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.EventDao;
import model.Event;
import requestresult.OneEventResult;

import java.sql.Connection;

/**
 * Processes OneEvent requests and results
 */
public class OneEventService {
    /** One Event Service
     * @return OneEventResult Object
     */
    OneEventResult getEvent(String eventID) {
        //ADD Authtoken verification
        OneEventResult result = new OneEventResult(false);
        Database db = new Database();
        try {
            //connect DAO to database
            Connection conn = db.getConnection();
            EventDao eDao = new EventDao(conn);

            //find event
            Event event = eDao.find(eventID);
            //put event info into result
            result.setInfo(event.getEventID(), event.getAssociatedUsername(), event.getPersonID(), event.getLatitude(),
                    event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());

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
