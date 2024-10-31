package ch.zero.project295.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zero.project295.model.Note;
import ch.zero.project295.repository.NoteRepository;
import ch.zero.project295.util.ApiResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/note")
public class NoteController {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Note>>> getAllNotes() {
        List<Note> noteList = noteRepository.findAll();
        ApiResponse<List<Note>> response = new ApiResponse<>(true, "successfully got all notes", noteList);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Note>> getNoteById(@PathVariable long id) {
        return noteRepository.findById(id)
                .map(note -> {
                    ApiResponse<Note> response = new ApiResponse<>(true, "Note with ID " + id + " found successfully", note);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Note>> createNote(@Valid @RequestBody Note note) {
        note.setCreatedDate(LocalDateTime.now());
        Note newNote = noteRepository.save(note);
        ApiResponse<Note> response = new ApiResponse<>(true, "Note created", newNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}/notetitle")
    public ResponseEntity<ApiResponse<Note>> updateNoteTitle(@PathVariable Long id,@Valid @RequestBody String noteTitle) {
        return noteRepository.findById(id)
            .map(existingNote -> {
                existingNote.setModifiedDate(LocalDateTime.now());
                existingNote.setNoteTitle(noteTitle);
                Note updatedNote = noteRepository.save(existingNote);
                ApiResponse<Note> response = new ApiResponse<>(true, "Note Title updated successfully for note with ID"  + id, updatedNote);
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }
    
    @PutMapping("{id}/noteBody")
    public ResponseEntity<ApiResponse<Note>> updateNoteBody(@PathVariable Long id,@Valid @RequestBody String noteBody) {
        return noteRepository.findById(id)
            .map(existingNote -> {
                existingNote.setModifiedDate(LocalDateTime.now());
                existingNote.setNoteBody(noteBody);
                Note updatedNote = noteRepository.save(existingNote);
                ApiResponse<Note> response = new ApiResponse<>(true, "Note Body updated successfully for note with ID"  + id, updatedNote);
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable long id) {
        return noteRepository.findById(id)
                .map(user -> {
                    noteRepository.delete(user);
                    ApiResponse<Void> response = new ApiResponse<>(true, "Note with ID " + id + " deleted successfully", null);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }
    
}
