package requestresult;

public class RegisterResult {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success;
    private String message;


    /**
     * Constructor that builds the object with the correct information
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     * @param message
     */
    public RegisterResult(String authtoken, String username, String personID, boolean success, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }
}
