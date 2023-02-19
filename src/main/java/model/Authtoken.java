package model;

//MAYBE UNNECESSARY?

import java.util.Objects;
public class Authtoken {
    private String authtoken;
    private String username;

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

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username);
    }

    @Override
    public String toString() {
        return "Authtoken{" +
                "authtoken='" + authtoken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authtoken authToken = (Authtoken) o;
        return Objects.equals(authtoken, authToken.authtoken) && Objects.equals(username, authToken.username);
    }
}
