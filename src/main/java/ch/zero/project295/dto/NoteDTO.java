package ch.zero.project295.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NoteDTO {

    private long noteId;

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 50, message = "Title cannot be longer than 50 characters")
    private String noteTitle;

    @NotNull(message = "body cannot be null")
    @NotBlank(message = "body cannot be empty")
    private String noteBody;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @NotNull(message = "User ID cannot be null")
    private long userId;

    @NotNull(message = "category ID cannot be null")
    private long categoryId;

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    
}
