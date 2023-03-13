package requestresult;

import model.Person;
/**
 * Object used in the AllPersons process to transfer result information
 */
public class AllPersonsResult extends Result {
    public AllPersonsResult(boolean success) {
        this.success = success;
    }

    private Person[] data;

    public Person[] getPersons() {
        return data;
    }

    public void setPersons(Person[] persons) {
        this.data = persons;
    }
}
