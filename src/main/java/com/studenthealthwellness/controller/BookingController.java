package com.studenthealthwellness.controller;

import com.studenthealthwellness.exception.BookingException;
import com.studenthealthwellness.model.Booking;
import com.studenthealthwellness.model.User;
import com.studenthealthwellness.service.BookingService;
import com.studenthealthwellness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Book a program for the authenticated user.
     * 
     * @param programId The ID of the program to book.
     * @param principal The authenticated user's principal.
     * @return A ResponseEntity with the booking details or an error message.
     */
    @PostMapping("/book/{programId}")
    public ResponseEntity<?> bookProgram(@PathVariable Long programId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(403).body("User not authenticated");
        }

        try {
            Long userId = getUserIdFromPrincipal(principal);
            Booking booking = bookingService.bookProgram(userId, programId);
            return ResponseEntity.ok(booking); // Return the created booking
        } catch (BookingException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return error message
        }
    }

    /**
     * Get all programs booked by the authenticated user.
     * 
     * @param principal The authenticated user's principal.
     * @return A ResponseEntity with the list of bookings.
     */
    @GetMapping("/my-programs")
    public ResponseEntity<List<Booking>> getMyPrograms(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(403).body(null); // User not authenticated
        }

        Long userId = getUserIdFromPrincipal(principal);
        List<Booking> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings); // Return list of user's bookings
    }

    /**
     * Helper method to extract the user ID from the Principal object.
     * 
     * @param principal The authenticated user's principal.
     * @return The ID of the user.
     */
    private Long getUserIdFromPrincipal(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new BookingException("User not found"); // Handle user not found
        }
        return user.getId();
    }
}
