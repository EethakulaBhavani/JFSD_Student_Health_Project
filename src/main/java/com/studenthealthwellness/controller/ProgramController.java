package com.studenthealthwellness.controller;

import com.studenthealthwellness.model.Program;
import com.studenthealthwellness.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    // Get all programs
    @GetMapping
    public List<Program> getAllPrograms() {
        return programService.getAllPrograms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgramById(@PathVariable Long id) {
        return programService.getProgramById(id)
                .map(program -> {
                    String imageUrl = "/images/" + program.getImageUrl();
                    program.setImageUrl(imageUrl);  // Ensure the image URL is correctly set
                    return ResponseEntity.ok(program);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    
    //Search programs by title or description
    @GetMapping("/search")
    public List<Program> searchPrograms(@RequestParam String query) {
        return programService.searchPrograms(query);
    }

    //Add a new program
    @PostMapping
    public ResponseEntity<Program> createProgram(@RequestBody Program program) {
        Program savedProgram = programService.saveProgram(program);
        return ResponseEntity.status(201).body(savedProgram);
    }

    //Update an existing program
    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable Long id, @RequestBody Program updatedProgram) {
        try {
            Program program = programService.updateProgram(id, updatedProgram);
            return ResponseEntity.ok(program);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a program by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }

    // Serve images (assuming they are in a directory on the server)
    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            // You need to implement logic to load images from a directory
            byte[] imageContent = Files.readAllBytes(Paths.get("student_wellness_project/frontend/src/images/" + imageName));
            return ResponseEntity.ok(imageContent);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
