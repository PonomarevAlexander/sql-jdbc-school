package com.foxminded.school.domain.models;

import java.util.Set;

public class Student {

    private int studentID;
    private String firstName;
    private String lastName;
    private int groupId;
    private Set<Integer> courses;
    
    public Student() {}
    
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public int getStudentID() {
        return studentID; 
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Set<Integer> getCourses() {
        return courses;
    }

    public void setCourses(Set<Integer> courses) {
        this.courses = courses;
    }   
}
