package com.studenthealthwellness.controller;

import com.studenthealthwellness.model.UpdateRequest;
import com.studenthealthwellness.model.User;
import com.studenthealthwellness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            User createdUser = userService.signup(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error during signup: " + e.getMessage());
        }
    }

    // Signin endpoint
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody User user) {
        Optional<User> loggedInUser = userService.signin(user.getEmail(), user.getPassword());
        if (loggedInUser.isPresent()) {
            return ResponseEntity.ok(loggedInUser.get());
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // Update username and password endpoint
    @PutMapping("/users/{id}/update")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateRequest updateRequest) {
        try {
            // Validate updateRequest fields
            if (updateRequest.getUsername() == null && updateRequest.getPassword() == null) {
                return ResponseEntity.badRequest().body("No fields to update");
            }

            // Call the service to update the user
            userService.updateUser(id, updateRequest.getUsername(), updateRequest.getPassword());
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error during update: " + e.getMessage());
        }
    }
}
