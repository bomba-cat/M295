package ch.zero.project295.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
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
import ch.zero.project295.model.User;
import ch.zero.project295.repository.NoteRepository;
import ch.zero.project295.repository.UserRepository;
import ch.zero.project295.util.EntityMapper;

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
    

    @Test
    void createNote_Success_WithUserId() throws Exception {
        // Arrange
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteTitle("Test Note");
        noteDTO.setNoteBody("This is a test note.");
        noteDTO.setUserId(1L);  // Set only the user ID

        // Mock the UserRepository to simulate the user exists
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        
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
}
