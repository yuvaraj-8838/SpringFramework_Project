
package com.example.StudentCourseRegistration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseDAO courseDAO;

    // Add Course
    @PostMapping
    public ResponseEntity<String> addCourse(
            @RequestBody Course course) {

        if (course.getCourseName() == null
                || course.getCourseName().isBlank()
                || course.getInstructor() == null
                || course.getInstructor().isBlank()
                || course.getDurationMonths() <= 0) {
            return ResponseEntity.badRequest()
                    .body("Enter valid course details");
        }

        courseDAO.addCourse(course);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Course added successfully");
    }

    // View All Courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    // View Course by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable int id) {
        Course course = courseDAO.getCourseById(id);

        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course not found");
        }

        return ResponseEntity.ok(course);
    }

    // Update Course
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(
            @PathVariable int id,
            @RequestBody Course course) {

        if (course.getCourseName() == null
                || course.getCourseName().isBlank()
                || course.getInstructor() == null
                || course.getInstructor().isBlank()
                || course.getDurationMonths() <= 0) {
            return ResponseEntity.badRequest()
                    .body("Enter valid course details");
        }

        int result = courseDAO.updateCourse(id, course);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course not found");
        }

        return ResponseEntity.ok("Course updated successfully");
    }

    // Delete Course
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        int result = courseDAO.deleteCourse(id);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course not found");
        }

        return ResponseEntity.ok("Course deleted successfully");
    }
}
