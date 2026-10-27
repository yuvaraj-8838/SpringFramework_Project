
package com.example.StudentCourseRegistration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/students")
@Validated
public class StudentController {

    @Autowired
    private StudentDAO studentDAO;

    // Add Student
    @PostMapping
    public ResponseEntity<String> addStudent(
            @Valid @RequestBody Student student) {

        studentDAO.addStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Student added successfully");
    }

    // View All Students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    // View Student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        Student student = studentDAO.getStudentById(id);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        return ResponseEntity.ok(student);
    }

    // Update Student
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(
            @PathVariable int id,
            @RequestBody Student student) {

        int result = studentDAO.updateStudent(id, student);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        return ResponseEntity.ok("Student updated successfully");
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {

        int result = studentDAO.deleteStudent(id);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        return ResponseEntity.ok("Student deleted successfully");
    }
}
