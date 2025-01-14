package ch.zero.project295.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.zero.project295.dto.NoteDTO;
import ch.zero.project295.model.Note;
import ch.zero.project295.model.Category;
import ch.zero.project295.repository.NoteRepository;
import ch.zero.project295.repository.UserRepository;
import ch.zero.project295.repository.CategoryRepository;
import ch.zero.project295.util.EntityMapper;

/**
 * Test class for NoteController, verifying the behavior of note-related endpoints.
 *
 * This class uses MockMvc to perform integration-like tests on the REST API endpoints defined in the NoteController.
 * Each test method is designed to simulate a specific use case, such as creating, retrieving, updating, or deleting notes.
 */
@WebMvcTest(NoteController.class)
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteRepository noteRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CategoryRepository categoryRepository;
    
    /**
     * Test case to verify the successful creation of a note with a given userId and categoryId.
     */
    @Test
    void createNote_Success_WithUserIdAndCategoryId() throws Exception {
        // Arrange
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteTitle("Test Note");
        noteDTO.setNoteBody("This is a test note.");
        noteDTO.setUserId(1L);  // Set only the user ID
        noteDTO.setCategoryId(2L);  // Set only the category ID

        // Mock the UserRepository and CategoryRepository to simulate the user and category exist
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(categoryRepository.existsById(2L)).thenReturn(true);
        
        // Mock the NoteRepository to return the note after saving
        Note note = EntityMapper.toNoteEntity(noteDTO);
        Mockito.when(noteRepository.save(Mockito.any(Note.class))).thenReturn(note);

        // Act & Assert
        mockMvc.perform(post("/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Note created"));
    }
    
    /**
     * Test case to verify failure when creating a note with an empty title.
     */
    @Test
    void createNote_Failure_TitleEmpty() throws Exception {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteId(1L);
        noteDTO.setNoteTitle("");
        noteDTO.setNoteBody("this is a Test body");

        mockMvc.perform(post("/note")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(noteDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Title cannot be empty"));
    }
        
    /**
     * Test case to verify successful retrieval of all notes.
     */
    @Test
    void getAllNotes_Success() throws Exception {
        // Arrange
        List<Note> notes = new ArrayList<>();
        Note note = new Note();
        note.setNoteId(1L);
        note.setNoteTitle("Test Note");
        note.setNoteBody("This is a test note.");
        notes.add(note);

        Mockito.when(noteRepository.findAll()).thenReturn(notes);

        // Act & Assert
        mockMvc.perform(get("/note"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    /**
     * Test case to verify successful retrieval of a note by its ID.
     */
    @Test
    void getNoteById_Success() throws Exception {
        // Arrange
        Note note = new Note();
        note.setNoteId(1L);
        note.setNoteTitle("Test Note");
        note.setNoteBody("This is a test note.");

        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        // Act & Assert
        mockMvc.perform(get("/note/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Note with ID 1 found successfully"));
    }

    /**
     * Test case to verify failure when attempting to retrieve a non-existent note by ID.
     */
    @Test
    void getNoteById_Failure_NotFound() throws Exception {
        // Arrange
        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/note/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Note with ID 1 not found"));
    }

    /**
     * Test case to verify successful deletion of a note.
     */
    @Test
    void deleteNote_Success() throws Exception {
        // Arrange
        Note note = new Note();
        note.setNoteId(1L);
        note.setNoteTitle("Test Note");

        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        // Act & Assert
        mockMvc.perform(delete("/note/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Note with ID 1 deleted successfully"));
    }

    /**
     * Test case to verify failure when attempting to delete a non-existent note.
     */
    @Test
    void deleteNote_Failure_NotFound() throws Exception {
        // Arrange
        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/note/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Note with ID 1 not found"));
    }

    /**
     * Test case to verify successful updating of a note's category.
     */
    @Test
    void updateNoteCategory_Success() throws Exception {
        System.out.println("Running updateNoteCategory_Success test");
        // Arrange
        Note note = new Note();
        note.setNoteId(1L);
        note.setNoteTitle("Test Note");
        note.setNoteBody("This is a test note.");

        Category oldCategory = new Category();
        oldCategory.setCategoryId(2L);
        note.setCategory(oldCategory);

        Category newCategory = new Category();
        newCategory.setCategoryId(3L);

        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        Mockito.when(categoryRepository.existsById(3L)).thenReturn(true);
        Mockito.when(categoryRepository.findById(3L)).thenReturn(Optional.of(newCategory));
        Mockito.when(noteRepository.save(Mockito.any(Note.class))).thenReturn(note);

        String requestBody = objectMapper.writeValueAsString(java.util.Map.of("categoryId", 3L));

        // Act & Assert
        mockMvc.perform(put("/note/{id}/category", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Note category updated successfully for note with ID 1"));
    }

    /**
     * Test case to verify failure when attempting to update a note's category with a non-existent category ID.
     */
    @Test
    void updateNoteCategory_Failure_CategoryNotFound() throws Exception {
        // Arrange
        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.of(new Note()));
        Mockito.when(categoryRepository.existsById(3L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(put("/note/{id}/category", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("categoryId", 3L))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid category ID"));
    }
}
