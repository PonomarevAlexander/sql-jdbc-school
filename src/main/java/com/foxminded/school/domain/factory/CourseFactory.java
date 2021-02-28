package com.foxminded.school.domain.factory;

import java.util.ArrayList;
import java.util.List;

import com.foxminded.school.domain.models.Course;

public class CourseFactory implements ModelFactory<List<Course>> {

    @Override
    public List<Course> generate(int counter) {
        List<Course> coursesList = new ArrayList<>();
        coursesList.add(new Course("Algebra"));
        coursesList.add(new Course("Literature"));
        coursesList.add(new Course("History"));
        coursesList.add(new Course("Physics"));
        coursesList.add(new Course("Music"));
        coursesList.add(new Course("Biology"));
        coursesList.add(new Course("Geography"));
        coursesList.add(new Course("Drawing"));
        coursesList.add(new Course("English"));
        coursesList.add(new Course("Science"));
        return coursesList;
    }

}
