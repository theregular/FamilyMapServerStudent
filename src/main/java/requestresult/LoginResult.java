package requestresult;
/**
 * Object used in the Login process to transfer result information
 */
public class LoginResult extends Result {
    private String authtoken;
    private String username;
    private String personID;

    /**
     * Constructor
     * @param success
     */
    public LoginResult(boolean success) {
        this.authtoken = null;
        this.username = null;
        this.personID = null;
        this.message = null;
        this.success = success;
    }

    /**
     * Fills with Login info (doesn't set message or success, use inherited methods for that)
     * @param authtoken
     * @param username
     * @param personID
     */
    public void setInfo(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
