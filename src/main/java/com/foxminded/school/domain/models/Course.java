package com.foxminded.school.domain.models;

public class Course {
    
    private int courseID;
    private String courseName;
    private String courseDescription;
    
    public Course() {}
    
    public Course(String courseName) {
        this.courseName = courseName;
    }
    
    public Course(int courseID, String courseName, String courseDescription) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
    
    
}
