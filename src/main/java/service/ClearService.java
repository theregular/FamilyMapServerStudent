package service;

import dataAccess.DataAccessException;
import dataAccess.Database;
import requestresult.ClearResult;
import requestresult.Result;

/**
 * Processes Clear requests and results
 */
public class ClearService {

    /** Clear Service
     * @return ClearResult Object
     */
    public ClearResult clear() {
        ClearResult result = new ClearResult(false);
        Database db = new Database();
        try {
            db.getConnection();

            db.clearTables();

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
