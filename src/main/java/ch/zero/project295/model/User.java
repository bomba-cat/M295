package ch.zero.project295.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Represents a user in the system.
 * <p>
 * This class holds the user details such as username, email, and password.
 * The class is annotated with JPA annotations for database mapping and 
 * validation constraints for ensuring the integrity of the data.
 * </p>
 */
@Entity
public class User {

    /**
     * Unique identifier for the user.
     * This field is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    /**
     * The username of the user.
     * <p>
     * Must be between 5 and 15 characters long and cannot be blank.
     * </p>
     */
    @NonNull
    @NotBlank(message = "User must have a username")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters.")
    private String username;

    /**
     * The email address of the user.
     * <p>
     * This field is required and must be a valid email format.
     * </p>
     */
    @NonNull
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    /**
     * The password of the user.
     * <p>
     * Must be between 5 and 40 characters long, cannot be blank,
     * and must contain at least one special character.
     * </p>
     */
    @NonNull
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 40, message = "The password must be between 5 and 40 characters")
    @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "Password must contain at least one special character.")
    private String password;
    
    /**
     * Gets the unique identifier for the user.
     * 
     * @return the user ID
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user.
     * 
     * @param userId the user ID to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the username of the user.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address of the user.
     * 
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * 
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Note> getNotes() {
        return notes;
    }
    
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}