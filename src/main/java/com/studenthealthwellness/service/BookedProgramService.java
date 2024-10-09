package com.studenthealthwellness.service;


import com.studenthealthwellness.model.BookedProgram;
import com.studenthealthwellness.repository.BookedProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookedProgramService {

    @Autowired
    private BookedProgramRepository bookedProgramRepository;

    public void save(BookedProgram bookedProgram) {
        // Save the booked program to the database
        bookedProgramRepository.save(bookedProgram);
    }

    // Updated method name and parameter
    public List<BookedProgram> findByUser(String username) {
        return bookedProgramRepository.findByUserUsername(username);
    }
}
