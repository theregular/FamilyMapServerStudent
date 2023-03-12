package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.LoadRequest;
import requestresult.LoadResult;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {
    private Database db;
    LoadRequest request;
    LoadRequest badRequest;
    LoadService service;
    LoadResult result;


    @BeforeEach
    public void setUp() throws FileNotFoundException, DataAccessException {
        db = new Database();
        Gson gson = new Gson();
        FileReader fileReader = new FileReader("passoffFiles/LoadData.json");
        request = gson.fromJson(fileReader, LoadRequest.class);
        badRequest = new LoadRequest(null, null, null);
        service = new LoadService();
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void SuccessfulLoad() throws DataAccessException {
        result = service.load(request);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Successfully added 2 users, 11 persons, and 19 events to the database.");
        assertTrue(result.isSuccess());
    }

    @Test
    public void FailedLoad() throws DataAccessException {
        result = service.load(badRequest);
        assertNotNull(result);
        assertEquals(result.getMessage(), "Error: Empty Request");
        assertFalse(result.isSuccess());
    }

}
