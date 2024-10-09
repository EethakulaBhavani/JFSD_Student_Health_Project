package com.studenthealthwellness.controller;

import com.studenthealthwellness.model.BookedProgram;
import com.studenthealthwellness.model.User;
import com.studenthealthwellness.service.BookedProgramService;
import com.studenthealthwellness.service.UserService;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {
    
    @Autowired
    private BookedProgramService bookedProgramService;
    
    @Autowired
    private UserService userService;

    @PostMapping("/book")
    public ResponseEntity<?> bookProgram(@RequestBody BookedProgram bookedProgram, Authentication authentication) {
        // Log the request and authentication status
        System.out.println("Booking program for user: " + authentication.getName());

        // Get the currently authenticated user
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        // Set the user in the bookedProgram entity
        bookedProgram.setUser(currentUser);

        // Save the booked program
        bookedProgramService.save(bookedProgram);
        
        System.out.println("Program booked successfully for: " + currentUser.getUsername());

        return ResponseEntity.ok("Program booked successfully");
    
    }
    
    @GetMapping("/booked")
    public ResponseEntity<List<BookedProgram>> getBookedPrograms(Authentication authentication) {
        String currentUsername = authentication.getName();

        // Use the username directly to fetch booked programs
        List<BookedProgram> bookedPrograms = bookedProgramService.findByUser(currentUsername);

        if (bookedPrograms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bookedPrograms);
        }

        return ResponseEntity.ok(bookedPrograms);
    }
}
