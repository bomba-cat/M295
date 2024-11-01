package ch.zero.project295.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Represents a note in the system.
 * <p>
 * Each note is associated with a specific user and contains details like title, body, and timestamps for creation and modification.
 * </p>
 */
@Entity
public class Note {

    /**
     * Unique identifier for the note.
     * This field is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noteId;

    /**
     * Title of the note.
     * <p>
     * The title cannot be empty and must be at most 50 characters long.
     * </p>
     */
    @NotNull
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 50, message = "Title cannot be longer than 50 characters")
    private String noteTitle;

    /**
     * Body of the note.
     * <p>
     * This field can hold the content of the note. It is not mandatory.
     * </p>
     */
    private String noteBody;

    /**
     * The creation date of the note.
     * <p>
     * This field cannot be null and indicates when the note was created.
     * </p>
     */
    private LocalDateTime createdDate;

    /**
     * The date when the note was last modified.
     * <p>
     * This field is optional and may not be set if the note has not been modified after creation.
     * </p>
     */
    private LocalDateTime modifiedDate;

    /**
     * The user associated with the note.
     * <p>
     * Each note is linked to a specific user, represented by the user ID.
     * </p>
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false )
    private User user;

    // Getters and setters

    /**
     * Gets the unique identifier for the note.
     *
     * @return the note ID
     */
    public long getNoteId() {
        return noteId;
    }

    /**
     * Sets the unique identifier for the note.
     *
     * @param noteId the note ID to set
     */
    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    /**
     * Gets the title of the note.
     *
     * @return the note title
     */
    public String getNoteTitle() {
        return noteTitle;
    }

    /**
     * Sets the title of the note.
     *
     * @param noteTitle the note title to set
     */
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    /**
     * Gets the body of the note.
     *
     * @return the note body
     */
    public String getNoteBody() {
        return noteBody;
    }

    /**
     * Sets the body of the note.
     *
     * @param noteBody the note body to set
     */
    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    /**
     * Gets the creation date of the note.
     *
     * @return the creation date
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the creation date of the note.
     *
     * @param createdDate the creation date to set
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the date when the note was last modified.
     *
     * @return the modification date
     */
    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the date when the note was last modified.
     *
     * @param modifiedDate the modification date to set
     */
    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * Gets the user associated with the note.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the note.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    @PrePersist
    public void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
    @PreUpdate
    public void onModified() {
        this.modifiedDate = LocalDateTime.now();
    }
}
