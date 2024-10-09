package com.studenthealthwellness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.studenthealthwellness.model.BookedProgram;
import com.studenthealthwellness.service.BookedProgramService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private BookedProgramService bookedProgramService;

    @GetMapping("/booked-programs")
    public ResponseEntity<List<BookedProgram>> getBookedPrograms(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername(); // Get the username from user details
        List<BookedProgram> bookedPrograms = bookedProgramService.findByUser(username); // Pass username instead of User object
        
        if (bookedPrograms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bookedPrograms);
        }
        return ResponseEntity.ok(bookedPrograms);
    }
}
