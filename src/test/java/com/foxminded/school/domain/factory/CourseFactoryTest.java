package com.foxminded.school.domain.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.school.domain.models.Course;

class CourseFactoryTest {

    private CourseFactory cFactory;
    
    @BeforeEach
    void init() {
        cFactory = new CourseFactory();
    }
    
    @Test
    void testGenerate() {
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
        List<Course> actual = cFactory.generate(10);
        assertEquals(10, actual.size());
    }
}
