package requestresult;

public class LoginResult {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success;
    private String message;

    /**
     * Constructor that takes parameters necessary to build the object
     * @param authtoken
     * @param username
     * @param personID
     * @param message
     * @param success
     */
    public LoginResult(String authtoken, String username, String personID, String message, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.message = message;
        this.success = success;
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
