package com.studenthealthwellness.repository;

import com.studenthealthwellness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Assuming this method exists
    Optional<User> findByUsername(String username); // Add this method to find user by username
}
