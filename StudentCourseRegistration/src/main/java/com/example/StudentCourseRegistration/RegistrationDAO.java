
package com.example.StudentCourseRegistration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Register Student for a Course
    public int registerStudent(Registration registration) {

        String sql = "INSERT INTO registrations "
                + "(student_id, course_id, registration_date) "
                + "VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql,
                registration.getStudentId(),
                registration.getCourseId(),
                registration.getRegistrationDate());
    }

    // View All Registrations
    public List<Registration> getAllRegistrations() {

        String sql = "SELECT * FROM registrations";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Registration(
                rs.getInt("registration_id"),
                rs.getInt("student_id"),
                rs.getInt("course_id"),
                rs.getDate("registration_date").toLocalDate()
            )
        );
    }

    // View Registrations for a Student
    public List<Registration> getRegistrationsByStudentId(int studentId) {

        String sql = "SELECT * FROM registrations WHERE student_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Registration(
                rs.getInt("registration_id"),
                rs.getInt("student_id"),
                rs.getInt("course_id"),
                rs.getDate("registration_date").toLocalDate()
            ),
            studentId);
    }

    // Cancel Registration
    public int cancelRegistration(int registrationId) {

        String sql = "DELETE FROM registrations "
                + "WHERE registration_id = ?";

        return jdbcTemplate.update(sql, registrationId);
    }
}
