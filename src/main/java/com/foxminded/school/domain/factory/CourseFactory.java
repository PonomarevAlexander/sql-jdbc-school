package com.foxminded.school.domain.factory;

import java.util.ArrayList;
import java.util.List;

import com.foxminded.school.domain.models.Course;

public class CourseFactory implements ModelFactory<List<Course>> {

    @Override
    public List<Course> generate(int counter) {
        List<Course> coursesList = new ArrayList<>();
        coursesList.add(new Course("Astronomy"));
        coursesList.add(new Course("Charms"));
        coursesList.add(new Course("dark arts"));
        coursesList.add(new Course("defence against the dark arts"));
        coursesList.add(new Course("herbology"));
        coursesList.add(new Course("history of magic"));
        coursesList.add(new Course("potions"));
        coursesList.add(new Course("transfiguration"));
        coursesList.add(new Course("alchemy"));
        coursesList.add(new Course("divination"));
        return coursesList;
    }

}
