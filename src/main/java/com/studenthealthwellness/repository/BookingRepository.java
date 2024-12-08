package com.studenthealthwellness.repository;

import com.studenthealthwellness.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    
        // Find a booking by both userId and programId
        Optional<Booking> findByUserIdAndProgramId(Long userId, Long programId);

}
