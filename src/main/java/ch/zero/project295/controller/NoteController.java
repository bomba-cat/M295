package ch.zero.project295.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.zero.project295.dto.NoteDTO;
import ch.zero.project295.model.Note;
import ch.zero.project295.repository.CategoryRepository;
import ch.zero.project295.repository.NoteRepository;
import ch.zero.project295.repository.UserRepository;
import ch.zero.project295.util.ApiResponse;
import ch.zero.project295.util.EntityMapper;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * NoteController handles all operations related to Notes in the system.
 * <p>
 * This includes creating, reading, updating, and deleting notes.
 * </p>
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all notes in the system.
     *
     * @return ResponseEntity containing ApiResponse with a list of all notes
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<NoteDTO>>> getAllNotes() {
        List<Note> noteList = noteRepository.findAll();
        List<NoteDTO> noteDTOList = EntityMapper.toNoteDTOList(noteList);
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>(true, "Successfully retrieved all notes", noteDTOList);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a note by its ID.
     *
     * @param id the ID of the note to retrieve
     * @return ResponseEntity containing ApiResponse with the note if found, or a 404 status if not found
     */
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

    /**
     * Creates a new note in the system.
     *
     * @param noteDTO the note information to create
     * @return ResponseEntity containing ApiResponse with the created note
     * 
     * Note: Ensure that the user and category IDs exist before attempting to create the note.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<NoteDTO>> createNote(@Valid @RequestBody NoteDTO noteDTO) {
        if (!userRepository.existsById(noteDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User with ID " + noteDTO.getUserId() + " not found", null));
        }
        if (!categoryRepository.existsById(noteDTO.getCategoryId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Category with ID " + noteDTO.getCategoryId() + " not found", null));
        }
        Note note = EntityMapper.toNoteEntity(noteDTO);
        note.setCreatedDate(LocalDateTime.now());
        Note newNote = noteRepository.save(note);
        NoteDTO saveNoteDTO = EntityMapper.toNoteDTO(newNote);
        ApiResponse<NoteDTO> response = new ApiResponse<>(true, "Note created", saveNoteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates the title of an existing note.
     *
     * @param id         the ID of the note to update
     * @param requestBody the request body containing the new note title
     * @return ResponseEntity containing ApiResponse with the updated note or a 404 status if not found
     * 
     * Note: Request body should contain a key-value pair for "noteTitle".
     */
    @PutMapping("{id}/notetitle")
    public ResponseEntity<ApiResponse<NoteDTO>> updateNoteTitle(@PathVariable Long id, @Valid @RequestBody Map<String, String> requestBody) {
        String noteTitle = requestBody.get("noteTitle");

        if (noteTitle == null || noteTitle.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Note title cannot be blank", null));
        }

        return noteRepository.findById(id)
            .map(existingNote -> {
                existingNote.setModifiedDate(LocalDateTime.now());
                existingNote.setNoteTitle(noteTitle);
                Note updatedNote = noteRepository.save(existingNote);
                NoteDTO updatedNoteDTO = EntityMapper.toNoteDTO(updatedNote);
                ApiResponse<NoteDTO> response = new ApiResponse<>(true, "Note Title updated successfully for note with ID " + id, updatedNoteDTO);
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }

    /**
     * Updates the body of an existing note.
     *
     * @param id         the ID of the note to update
     * @param requestBody the request body containing the new note body
     * @return ResponseEntity containing ApiResponse with the updated note or a 404 status if not found
     * 
     * Note: Request body should contain a key-value pair for "noteBody".
     */
    @PutMapping("{id}/notebody")
    public ResponseEntity<ApiResponse<NoteDTO>> updateNoteBody(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String noteBody = requestBody.get("noteBody");

        if (noteBody == null || noteBody.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Note body cannot be blank", null));
        }

        return noteRepository.findById(id)
            .map(existingNote -> {
                existingNote.setModifiedDate(LocalDateTime.now());
                existingNote.setNoteBody(noteBody);
                Note updatedNote = noteRepository.save(existingNote);
                NoteDTO updatedNoteDTO = EntityMapper.toNoteDTO(updatedNote);
                ApiResponse<NoteDTO> response = new ApiResponse<>(true, "Note body updated successfully for note with ID " + id, updatedNoteDTO);
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }

    /**
     * Updates the category of an existing note.
     *
     * @param id         the ID of the note to update
     * @param requestBody the request body containing the new category ID
     * @return ResponseEntity containing ApiResponse with the updated note or a 404 status if not found
     * 
     * Note: Ensure the category ID exists before attempting to update the note's category.
     */
    @PutMapping("{id}/category")
    public ResponseEntity<ApiResponse<NoteDTO>> updateNoteCategory(@PathVariable Long id, @RequestBody Map<String, Long> requestBody) {
        Long categoryId = requestBody.get("categoryId");

        if (categoryId == null || !categoryRepository.existsById(categoryId)) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid category ID", null));
        }

        return noteRepository.findById(id)
            .map(existingNote -> {
                existingNote.setModifiedDate(LocalDateTime.now());
                existingNote.setCategory(categoryRepository.findById(categoryId).orElse(null));
                Note updatedNote = noteRepository.save(existingNote);
                NoteDTO updatedNoteDTO = EntityMapper.toNoteDTO(updatedNote);
                ApiResponse<NoteDTO> response = new ApiResponse<>(true, "Note category updated successfully for note with ID " + id, updatedNoteDTO);
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }

    /**
     * Deletes a note by its ID.
     *
     * @param id the ID of the note to delete
     * @return ResponseEntity containing ApiResponse with no content or a 404 status if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable long id) {
        return noteRepository.findById(id)
                .map(note -> {
                    noteRepository.delete(note);
                    ApiResponse<Void> response = new ApiResponse<>(true, "Note with ID " + id + " deleted successfully", null);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Note with ID " + id + " not found", null)));
    }
}
