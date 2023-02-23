package model;

//MAYBE UNNECESSARY?

import java.util.Objects;

/**
 * Authtoken model object used to store info passed from database
 */
public class Authtoken {
    private String authtoken;
    private String username;

    /**
     * Constructor to build object
     * @param authtoken
     * @param username
     */

    public Authtoken (String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns a hashcode for the authtoken object
     * @return int hashcode
     */

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username);
    }


    /**
     * Returns a string representation of the authtoken object
     * @return String
     */
    @Override
    public String toString() {
        return "Authtoken{" +
                "authtoken='" + authtoken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    /**
     * Returns a boolean for comparision of authtoken objects
     * @return boolean
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authtoken authToken = (Authtoken) o;
        return Objects.equals(authtoken, authToken.authtoken) && Objects.equals(username, authToken.username);
    }
}
