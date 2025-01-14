package ch.zero.project295.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Represents a user in the system, including username, email, and password details.
 * Ensures integrity through various constraints such as username length and email format.
 */

@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @NonNull
    @NotBlank(message = "User must have a username")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters.")
    private String username;

    @NonNull
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;


    @NonNull
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 40, message = "The password must be between 5 and 40 characters")
    @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "Password must contain at least one special character.")
    private String password;
    
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}