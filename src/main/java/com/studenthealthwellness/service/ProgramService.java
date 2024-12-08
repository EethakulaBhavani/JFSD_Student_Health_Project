package com.studenthealthwellness.service;

import com.studenthealthwellness.model.Program;
import com.studenthealthwellness.repository.ProgramRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    // Retrieve all programs
    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }
    
    // Initialize default programs if not already present
    @PostConstruct
    public void init() {
        // Check if there are already programs in the database
        if (programRepository.count() == 0) {
            // Add 9 default programs
            programRepository.save(new Program("Mindfulness and Stress Management", "This program focuses on teaching students mindfulness techniques...", "3 weeks", 100.00, "pic1.jpg"));
            programRepository.save(new Program("Physical Fitness and Active Living", "Promoting physical health through regular exercise and active living...", "2 weeks", 80.00, "pic2.jpg"));
            programRepository.save(new Program("Healthy Eating & Nutrition Education", "This program educates students about balanced diets, nutrition labels...", "4 weeks", 120.00, "pic3.jpg"));
            programRepository.save(new Program("Mental Health Counseling & Support", "Offering access to licensed counselors, this program provides one-on-one sessions...", "5 weeks", 150.00, "pic4.jpg"));
            programRepository.save(new Program("Sleep Hygiene and Restful Living", "This program helps students develop better sleep habits by teaching the importance of sleep...", "1 week", 50.00, "pic5.jpg"));
            programRepository.save(new Program("Substance Awareness & Support", "Designed to educate students on the risks associated with substance abuse...", "6 weeks", 180.00, "pic6.jpg"));
            programRepository.save(new Program("Digital Detox and Balance", "This program helps students manage screen time and reduce digital overload..", "2 weeks", 90.00, "pic7.jpg"));
            programRepository.save(new Program("Self-Care and Personal Growth", "Focusing on self-care practices, this program guides students through various activities...", "3 weeks", 110.00, "pic8.jpg"));
            programRepository.save(new Program("Emotional Resilience Building", "This program focuses on helping students build emotional resilience...", "4 weeks", 130.00, "pic9.jpg"));
        }
    }
    

    // Retrieve a program by ID
    public Optional<Program> getProgramById(Long id) {
        return programRepository.findById(id);
    }
    
    public List<Program> searchPrograms(String query) {
        List<Program> allPrograms = programRepository.findAll();
        return allPrograms.stream()
                .filter(program -> program.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        program.getDescription().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
    // Save a new program
    public Program saveProgram(Program program) {
        return programRepository.save(program);
    }

    // Update an existing program
    public Program updateProgram(Long id, Program updatedProgram) {
        return programRepository.findById(id).map(program -> {
            program.setTitle(updatedProgram.getTitle());
            program.setDescription(updatedProgram.getDescription());
            program.setDuration(updatedProgram.getDuration());
            program.setCost(updatedProgram.getCost());
            return programRepository.save(program);
        }).orElseThrow(() -> new RuntimeException("Program not found with id " + id));
    }

    // Delete a program by ID
    public void deleteProgram(Long id) {
        programRepository.deleteById(id);
    }
}
