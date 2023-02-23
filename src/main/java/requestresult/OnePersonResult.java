package requestresult;
/**
 * Object used in the OnePerson process to transfer result information
 */
public class OnePersonResult {
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
     * Constructor that takes parameters necessary to build the object
     * @param associatedUsername
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     * @param success
     * @param message
     */
    public OnePersonResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, boolean success, String message) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
        this.message = message;
    }
}
