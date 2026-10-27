
package com.example.StudentCourseRegistration;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add Student
    public int addStudent(Student student) {
        String sql = "INSERT INTO students "
                + "(student_name, email, department) "
                + "VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql,
                student.getStudentName(),
                student.getEmail(),
                student.getDepartment());
    }

    // View All Students
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Student(
                rs.getInt("student_id"),
                rs.getString("student_name"),
                rs.getString("email"),
                rs.getString("department")
            )
        );
    }

    // View Student by ID
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        List<Student> students = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Student(
                    rs.getInt("student_id"),
                    rs.getString("student_name"),
                    rs.getString("email"),
                    rs.getString("department")
                ),
                studentId);

        return students.isEmpty() ? null : students.get(0);
    }

    // Update Student
    public int updateStudent(int studentId, Student student) {
        String sql = "UPDATE students SET student_name = ?, "
                + "email = ?, department = ? WHERE student_id = ?";

        return jdbcTemplate.update(sql,
                student.getStudentName(),
                student.getEmail(),
                student.getDepartment(),
                studentId);
    }

    // Delete Student
    public int deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        return jdbcTemplate.update(sql, studentId);
    }
}
