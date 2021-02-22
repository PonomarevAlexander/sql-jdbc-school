package com.foxminded.school.domain.models;

public class Course {
    
    private int id;
    private String name;
    private String courseDescription;
    
    public Course() {}
    
    public Course(String courseName) {
        this.name = courseName;
    }
    
    public Course(int courseID, String courseName, String courseDescription) {
        this.id = courseID;
        this.name = courseName;
        this.courseDescription = courseDescription;
    }

    public int getCourseID() {
        return id;
    }

    public String getCourseName() {
        return name;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseID(int courseID) {
        this.id = courseID;
    }

    public void setCourseName(String courseName) {
        this.name = courseName;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
    
    
}
