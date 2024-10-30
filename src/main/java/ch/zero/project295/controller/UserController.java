package ch.zero.project295.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.*;

import ch.zero.project295.model.User;
import ch.zero.project295.repository.UserRepository;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }
    @PutMapping("/{id}/username")
    public ResponseEntity<User> updateUsername(@PathVariable Long id, @Valid @RequestBody String username) {
        return userRepository.findById(id)
        .map(existingUser -> {
            existingUser.setUsername(username);
            User updatedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        })
        .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}/email")
    public ResponseEntity<User> updateEmail(@PathVariable long id, @Valid @RequestBody String email) {
        return userRepository.findById(id)
        .map(existingUser -> {
            existingUser.setEmail(email);
            User updatedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        })
        .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<User> updatePassword(@PathVariable long id, @Valid @RequestBody String password) {
        return userRepository.findById(id)
        .map(existingUser -> {
            existingUser.setPassword(password);
            User updatedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        })
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        return userRepository.findById(id)
        .map( user -> {
            userRepository.delete(user);
            return ResponseEntity.noContent().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());    
    }
    

}
