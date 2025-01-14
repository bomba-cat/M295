package ch.zero.project295.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.zero.project295.dto.UserDTO;
import ch.zero.project295.model.User;
import ch.zero.project295.repository.UserRepository;
import ch.zero.project295.util.EntityMapper;

import java.util.Optional;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;
    
    @Test
    void register_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("email@example.com");
        userDTO.setUsername("UserNameExample");
        userDTO.setPassword("P@ssw0rd");
        userDTO.setUserId(1L);

        User user = EntityMapper.toUserEntity(userDTO);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User registered successfully with ID 1"));
    }

    @Test
    void register_Failure_MissingFields() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("");  // Missing required fields
        userDTO.setUsername("");
        userDTO.setPassword("");

        mockMvc.perform(post("/user/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value(Matchers.containsString("Username must not be blank")))
        .andExpect(jsonPath("$.message").value(Matchers.containsString("Email is required")))
        .andExpect(jsonPath("$.message").value(Matchers.containsString("Password cannot be empty")));
    }

    @Test
    void getUserById_Success() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("UserNameExample");
        user.setEmail("email@example.com");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User with ID 1 found successfully"))
                .andExpect(jsonPath("$.data.username").value("UserNameExample"))
                .andExpect(jsonPath("$.data.email").value("email@example.com"));
    }

    @Test
    void getUserById_Failure_NotFound() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User with ID 1 not found"));
    }

    @Test
    void updateEmail_Success() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("UserNameExample");
        user.setEmail("oldemail@example.com");

        String newEmail = "newemail@example.com";
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(put("/user/{id}/email", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmail)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Email updated successfully for user with ID 1"));
    }

    @Test
    void updateEmail_Failure_UserNotFound() throws Exception {
        String newEmail = "newemail@example.com";
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/user/{id}/email", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmail)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User with ID 1 not found"));
    }

    @Test
    void deleteUser_Success() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("UserNameExample");
        user.setEmail("email@example.com");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/user/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User with ID 1 deleted successfully"));
    }

    @Test
    void deleteUser_Failure_UserNotFound() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/user/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User with ID 1 not found"));
    }
}
