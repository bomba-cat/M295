package ch.zero.project295.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ch.zero.project295.model.Note;
import ch.zero.project295.util.EntityMapper;

public class NoteDTO {
    private long noteId;
    private String noteTitle;
    private String noteBody;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private UserDTO user;


    public NoteDTO() {
        // no constructor so that the json can fill it
    }


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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}
