package ch.zero.project295.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zero.project295.dto.NoteDTO;
import ch.zero.project295.model.Note;
import ch.zero.project295.repository.NoteRepository;
import ch.zero.project295.repository.UserRepository;
import ch.zero.project295.util.ApiResponse;
import ch.zero.project295.util.EntityMapper;
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
    private final UserRepository userRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository, UserRepository userRepository ) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<NoteDTO>>> getAllNotes() {
        List<Note> noteList = noteRepository.findAll();
        
        List<NoteDTO> noteDTOList = EntityMapper.toNoteDTOList(noteList);

        ApiResponse<List<NoteDTO>> response = new ApiResponse<>(true, "Successfully retrieved all notes", noteDTOList);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteDTO>> getNoteById(@PathVariable long id) {
        return noteRepository.findById(id)
                .map(note -> {
                    NoteDTO noteDTO = EntityMapper.toNoteDTO(note);
                    ApiResponse<NoteDTO> response = new ApiResponse<>(true, "Note with ID " + id + " found successfully", noteDTO);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<NoteDTO>> createNote(@Valid @RequestBody NoteDTO noteDTO) {
        if (!userRepository.existsById(noteDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User with ID " + noteDTO.getUserId() + " not found", null));
        }        
        Note note = EntityMapper.toNoteEntity(noteDTO);
        note.setCreatedDate(LocalDateTime.now());
        Note newNote = noteRepository.save(note);
        NoteDTO saveNoteDTO = EntityMapper.toNoteDTO(newNote);
        ApiResponse<NoteDTO> response = new ApiResponse<>(true, "Note created", saveNoteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}/notetitle")
    public ResponseEntity<ApiResponse<NoteDTO>> updateNoteTitle(@PathVariable Long id,@Valid @RequestBody String noteTitle) {
        return noteRepository.findById(id)
            .map(existingNote -> {
                existingNote.setModifiedDate(LocalDateTime.now());
                existingNote.setNoteTitle(noteTitle);
                Note updatedNote = noteRepository.save(existingNote);
                NoteDTO updatedNoteDTO = EntityMapper.toNoteDTO(updatedNote);
                ApiResponse<NoteDTO> response = new ApiResponse<>(true, "Note Title updated successfully for note with ID"  + id, updatedNoteDTO);
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }
    
    @PutMapping("{id}/notebody")
    public ResponseEntity<ApiResponse<NoteDTO>> updateNoteBody(@PathVariable Long id,@Valid @RequestBody String noteBody) {
        return noteRepository.findById(id)
            .map(existingNote -> {
                existingNote.setModifiedDate(LocalDateTime.now());
                existingNote.setNoteBody(noteBody);
                Note updatedNote = noteRepository.save(existingNote);
                NoteDTO updatedNoteDTO = EntityMapper.toNoteDTO(updatedNote);
                ApiResponse<NoteDTO> response = new ApiResponse<>(true, "Note Body updated successfully for note with ID"  + id, updatedNoteDTO);
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
