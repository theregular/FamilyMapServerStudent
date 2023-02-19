package requestresult;

public class LoginRequest {
    private String username;
    private String password;

    /**
     * Constructor that takes parameters necessary to build the object
     * @param username String
     * @param password String
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
