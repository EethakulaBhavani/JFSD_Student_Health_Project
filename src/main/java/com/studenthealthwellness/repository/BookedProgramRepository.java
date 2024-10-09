package com.studenthealthwellness.repository;

import com.studenthealthwellness.model.BookedProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedProgramRepository extends JpaRepository<BookedProgram, Long> {
    // Corrected method to navigate through the 'user' association
    List<BookedProgram> findByUserUsername(String username);
}
