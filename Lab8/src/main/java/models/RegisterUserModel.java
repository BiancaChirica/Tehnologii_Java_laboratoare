package models;

import javax.enterprise.inject.Default;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Default
public class RegisterUserModel implements Serializable {

    @NotNull(message = "The username must be provided")
    @Size(min = 5, max = 20, message = "The username must be between 5 and 20 characters long")
    @Pattern(message = "The username must contain only alphanumeric characters", regexp = "[0-9a-zA-Z]+")
    private String username;
    @NotNull(message = "The password must be provided")
    @Size(min = 3, max = 15, message = "The password must be between 3 and 15 characters long")
    @Pattern(message = "The password must contain only alphanumeric characters", regexp = "[0-9a-zA-Z]+")
    private String password;

    private String role;

    public RegisterUserModel() {

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
