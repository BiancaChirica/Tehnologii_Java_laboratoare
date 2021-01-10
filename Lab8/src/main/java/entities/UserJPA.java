package entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserJPA implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "role",nullable = false)
    private String role;


    public UserJPA()
    {

    }
    public UserJPA( String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJPA userJPA = (UserJPA) o;
        return Objects.equals(id, userJPA.id) &&
                Objects.equals(username, userJPA.username) &&
                Objects.equals(password, userJPA.password) &&
                Objects.equals(role, userJPA.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role);
    }
}
