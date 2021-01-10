package models;

import javax.enterprise.inject.Default;
import java.io.Serializable;

@Default
public class User implements Serializable {

    private String username;
    private String password;
    private boolean loggedOn;
    private String role;

    public User() {

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

    public boolean isLoggedOn() {
        return loggedOn;
    }

    public void setLoggedOn(boolean loggedOn) {
        this.loggedOn = loggedOn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
