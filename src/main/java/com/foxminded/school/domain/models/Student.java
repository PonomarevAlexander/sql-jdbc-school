package com.foxminded.school.domain.models;

import java.util.Set;

public class Student {

    private int id;
    private String name;
    private String lastName;
    private int groupId;
    private Set<Integer> courses;
    
    public Student() {}
    
    public Student(String firstName, String lastName) {
        this.name = firstName;
        this.lastName = lastName;
    }
    
    public int getStudentID() {
        return id; 
    }

    public String getFirstName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setStudentID(int studentID) {
        this.id = studentID;
    }

    public void setFirstName(String firstName) {
        this.name = firstName;
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
