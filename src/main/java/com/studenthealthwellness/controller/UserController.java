package com.studenthealthwellness.controller;

import com.studenthealthwellness.model.Program;
import com.studenthealthwellness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to get booked programs for the authenticated user
    @GetMapping("/my-booked-programs")
    public ResponseEntity<?> getMyBookedPrograms(@AuthenticationPrincipal UserDetails userDetails) {
        List<Program> bookedPrograms = userService.getBookedProgramsForUser(userDetails.getUsername());
        
        // Check if the list is empty and respond accordingly
        if (bookedPrograms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No booked programs found.");
        }
        return ResponseEntity.ok(bookedPrograms);
    }
}
