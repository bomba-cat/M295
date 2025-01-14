package ch.zero.project295.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import ch.zero.project295.dto.UserDTO;
import ch.zero.project295.model.User;
import ch.zero.project295.repository.UserRepository;
import ch.zero.project295.util.ApiResponse;
import ch.zero.project295.util.EntityMapper;

/**
 * UserController handles all user-related operations in the system, 
 * including registration, updating user information, and deleting users.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users in the system.
     *
     * @return ResponseEntity containing ApiResponse with a list of all users
     */
    @Operation(summary = "Get all users", description = "Retrieves all users in the system")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userListDTO = EntityMapper.toUserDTOList(userList);
        ApiResponse<List<UserDTO>> response = new ApiResponse<>(true, "Users retrieved successfully", userListDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return ResponseEntity containing ApiResponse with the user if found, or 404 status if not found
     */
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable long id) {
        return userRepository.findById(id)
                .map(user -> {
                    UserDTO userDTO = EntityMapper.toUserDTO(user);
                    ApiResponse<UserDTO> response = new ApiResponse<>(true, "User with ID " + id + " found successfully", userDTO);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User with ID " + id + " not found", null)));
    }

    /**
     * Registers a new user in the system.
     *
     * @param userDTO the user information to register
     * @return ResponseEntity containing ApiResponse with the registered user
     */
    @Operation(summary = "Register a new user", description = "Registers a new user in the system")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> registerUser(@Valid @RequestBody UserDTO userDTO) {
        System.out.println("Received password: " + userDTO.getPassword()); // Debugging purpose; avoid logging passwords in production.
        User user = EntityMapper.toUserEntityRegistration(userDTO);
        User registeredUser = userRepository.save(user);
        UserDTO registeredUserDTO = EntityMapper.toUserDTO(registeredUser);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User registered successfully with ID " + registeredUser.getUserId(), registeredUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates the username of an existing user.
     *
     * @param id       the ID of the user to update
     * @param username the new username
     * @return ResponseEntity containing ApiResponse with the updated user or a 404 status if not found
     */
    @Operation(summary = "Update username", description = "Updates the username of an existing user")
    @PutMapping("/{id}/username")
    public ResponseEntity<ApiResponse<UserDTO>> updateUsername(@PathVariable Long id, @Valid @RequestBody String username) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(username);
                    User updatedUser = userRepository.save(existingUser);
                    UserDTO updatedUserDTO = EntityMapper.toUserDTO(updatedUser);
                    ApiResponse<UserDTO> response = new ApiResponse<>(true, "Username updated successfully for user with ID " + id, updatedUserDTO);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User with ID " + id + " not found", null)));
    }

    /**
     * Updates the email address of an existing user.
     *
     * @param id    the ID of the user to update
     * @param email the new email address
     * @return ResponseEntity containing ApiResponse with the updated user or a 404 status if not found
     */
    @Operation(summary = "Update email", description = "Updates the email address of an existing user")
    @PutMapping("/{id}/email")
    public ResponseEntity<ApiResponse<UserDTO>> updateEmail(@PathVariable long id, @Valid @RequestBody String email) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(email);
                    User updatedUser = userRepository.save(existingUser);
                    UserDTO updatedUserDTO = EntityMapper.toUserDTO(updatedUser);
                    ApiResponse<UserDTO> response = new ApiResponse<>(true, "Email updated successfully for user with ID " + id, updatedUserDTO);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User with ID " + id + " not found", null)));
    }

    /**
     * Updates the password of an existing user.
     *
     * @param id       the ID of the user to update
     * @param password the new password
     * @return ResponseEntity containing ApiResponse with the updated user or a 404 status if not found
     */
    @Operation(summary = "Update password", description = "Updates the password of an existing user")
    @PutMapping("/{id}/password")
    public ResponseEntity<ApiResponse<UserDTO>> updatePassword(@PathVariable long id, @Valid @RequestBody String password) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setPassword(password);
                    User updatedUser = userRepository.save(existingUser);
                    UserDTO updatedUserDTO = EntityMapper.toUserDTO(updatedUser);
                    ApiResponse<UserDTO> response = new ApiResponse<>(true, "Password updated successfully for user with ID " + id, updatedUserDTO);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User with ID " + id + " not found", null)));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return ResponseEntity containing ApiResponse with no content or a 404 status if not found
     */
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    ApiResponse<Void> response = new ApiResponse<>(true, "User with ID " + id + " deleted successfully", null);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User with ID " + id + " not found", null)));
    }
}
