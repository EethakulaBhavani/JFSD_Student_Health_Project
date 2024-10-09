package com.studenthealthwellness.service;

import com.studenthealthwellness.model.User;
import com.studenthealthwellness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Signup method with password encryption
    public User signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        return userRepository.save(user);
    }

    // Signin method with password matching
    public Optional<User> signin(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email); // Now returns Optional<User>
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user; // Return Optional<User> if password matches
        }
        return Optional.empty(); // Return empty Optional if authentication fails
    }

    // Method to find a user by username for authentication
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null); // Return user if present, else return null
    }

    // Method to update user details
    public void updateUser(Long id, String username, String password) {
        // Find the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update the username if it is not null or empty
        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        }
        // Update the password if it is not null and encode it
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password)); // Encode the new password
        }

        // Save the updated user back to the database
        userRepository.save(user);
    }
}
