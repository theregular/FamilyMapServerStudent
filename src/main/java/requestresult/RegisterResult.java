package requestresult;
/**
 * Object used in the Register process to transfer result information
 */
public class RegisterResult extends Result{
    private String authtoken;
    private String username;
    private String personID;


    /**
     * Constructor
     * @param success
     */
    public RegisterResult(boolean success) {
        this.authtoken = null;
        this.username = null;
        this.personID = null;
        this.success = success;
        this.message = null;
    }
    /**
     * Fills with Register info (doesn't set message or success, use inherited methods for that)
     * @param authtoken
     * @param username
     * @param personID
     */
    public void setInfo(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }
}
