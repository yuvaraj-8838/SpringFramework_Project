
package com.example.StudentCourseRegistration;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private CourseDAO courseDAO;

    // Register Student for a Course
    @PostMapping
    public ResponseEntity<String> registerStudent(
            @RequestBody Registration registration) {

        if (registration.getStudentId() <= 0
                || registration.getCourseId() <= 0) {
            return ResponseEntity.badRequest()
                    .body("Valid student ID and course ID are required");
        }

        if (studentDAO.getStudentById(registration.getStudentId()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        if (courseDAO.getCourseById(registration.getCourseId()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course not found");
        }

        registration.setRegistrationDate(LocalDate.now());

        registrationDAO.registerStudent(registration);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Student registered for course successfully");
    }

    // View All Registrations
    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationDAO.getAllRegistrations();
    }

    // View Registrations by Student ID
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getRegistrationsByStudentId(
            @PathVariable int studentId) {

        if (studentDAO.getStudentById(studentId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        return ResponseEntity.ok(
                registrationDAO.getRegistrationsByStudentId(studentId));
    }

    // Cancel Registration
    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> cancelRegistration(
            @PathVariable int registrationId) {

        int result = registrationDAO.cancelRegistration(registrationId);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Registration not found");
        }

        return ResponseEntity.ok("Registration cancelled successfully");
    }
}
