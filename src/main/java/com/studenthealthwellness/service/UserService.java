package com.studenthealthwellness.service;

import com.studenthealthwellness.model.Booking;
import com.studenthealthwellness.model.Program;
import com.studenthealthwellness.model.User;
import com.studenthealthwellness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //signup method
    public User signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
        return userRepository.save(user);
    }

    //signin method
    public Optional<User> signin(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return Optional.of(user); // Return the authenticated user
        }
        return Optional.empty(); // Return empty if authentication fails
    }
    
 //Update User method
    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            return userRepository.save(existingUser);
        });
    }

    //Delete User method
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Program> getBookedProgramsForUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            //Map List<Booking> to List<Program>
            return user.getBookings().stream()
                    .map(Booking::getProgram) // Assuming getProgram() returns Program
                    .collect(Collectors.toList());
        }
        return List.of(); // Return empty list if user not found
    }
}
