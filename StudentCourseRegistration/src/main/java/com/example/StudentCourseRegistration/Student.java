
package com.example.StudentCourseRegistration;

public class Student {

    private int studentId;
    private String studentName;
    private String email;
    private String department;

    public Student() {
    }

    public Student(int studentId, String studentName,
                   String email, String department) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.department = department;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
