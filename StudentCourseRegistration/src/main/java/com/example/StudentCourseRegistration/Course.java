
package com.example.StudentCourseRegistration;

public class Course {

    private int courseId;
    private String courseName;
    private String instructor;
    private int durationMonths;

    public Course() {
    }

    public Course(int courseId, String courseName,
                  String instructor, int durationMonths) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.durationMonths = durationMonths;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }
}
