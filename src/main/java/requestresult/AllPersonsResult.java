package requestresult;

import model.Person;
/**
 * Object used in the AllPersons process to transfer result information
 */
public class AllPersonsResult {
    private boolean success;
    private String message;
    private Person[] persons;
}
