package com.foxminded.school.domain;

import java.util.List;
import java.util.StringJoiner;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Student;

public class ConsoleFormatter {
    
    private static final String NEW_LINE = "\n";
    private static final String DELIMITER = " - ";
    private static final String SPACE = " ";

    public String formatFor2ndMenuItem(List<Student> list) {
        StringJoiner joiner = new StringJoiner(NEW_LINE);
        list.forEach(student -> joiner.add(student.getFirstName() + SPACE + student.getLastName()));
        return joiner.toString();
    }
    
    public String formatCoursesList(List<Course> coursesList) {
        StringJoiner joiner = new StringJoiner(NEW_LINE);
        coursesList.forEach(course -> joiner.add(course.getCourseID() + DELIMITER + course.getCourseName()));
        return joiner.toString();
    }
}
