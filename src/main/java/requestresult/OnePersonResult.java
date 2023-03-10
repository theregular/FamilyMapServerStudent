package requestresult;
/**
 * Object used in the OnePerson process to transfer result information
 */
public class OnePersonResult extends Result{
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID; //[OPTIONAL, can be missing]
    private String motherID; //[OPTIONAL, can be missing]
    private String spouseID; //[OPTIONAL, can be missing]
    private boolean success;
    private String message;


    /**
     * Constructor
     * @param success
     */
    public OnePersonResult(boolean success) {
        this.associatedUsername = null;
        this.personID = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
        this.success = success;
        this.message = null;
    }

    /**
     * Fills with Person info except for fatherID, motherID, and spouseID (doesn't set message or success, use inherited methods for that)
     * @param associatedUsername
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     */
    public void setInfo(String associatedUsername, String personID, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
