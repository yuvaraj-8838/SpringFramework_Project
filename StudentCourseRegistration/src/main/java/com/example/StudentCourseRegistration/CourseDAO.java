
package com.example.StudentCourseRegistration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add Course
    public int addCourse(Course course) {
        String sql = "INSERT INTO courses "
                + "(course_name, instructor, duration_months) "
                + "VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql,
                course.getCourseName(),
                course.getInstructor(),
                course.getDurationMonths());
    }

    // View All Courses
    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM courses";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Course(
                rs.getInt("course_id"),
                rs.getString("course_name"),
                rs.getString("instructor"),
                rs.getInt("duration_months")
            )
        );
    }

    // View Course by ID
    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";

        List<Course> courses = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getString("instructor"),
                    rs.getInt("duration_months")
                ),
                courseId);

        return courses.isEmpty() ? null : courses.get(0);
    }

    // Update Course
    public int updateCourse(int courseId, Course course) {
        String sql = "UPDATE courses SET course_name = ?, "
                + "instructor = ?, duration_months = ? "
                + "WHERE course_id = ?";

        return jdbcTemplate.update(sql,
                course.getCourseName(),
                course.getInstructor(),
                course.getDurationMonths(),
                courseId);
    }

    // Delete Course
    public int deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        return jdbcTemplate.update(sql, courseId);
    }
}
