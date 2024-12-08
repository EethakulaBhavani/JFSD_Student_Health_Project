package com.studenthealthwellness.service;

import com.studenthealthwellness.exception.BookingException;
import com.studenthealthwellness.model.Booking;
import com.studenthealthwellness.model.Program;
import com.studenthealthwellness.model.User;
import com.studenthealthwellness.repository.BookingRepository;
import com.studenthealthwellness.repository.ProgramRepository;
import com.studenthealthwellness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private UserRepository userRepository;

    public Booking bookProgram(Long userId, Long programId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BookingException("User not found"));
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new BookingException("Program not found"));

        if (bookingRepository.findByUserIdAndProgramId(userId, programId).isPresent()) {
            throw new BookingException("You have already booked this program");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setProgram(program);
        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
