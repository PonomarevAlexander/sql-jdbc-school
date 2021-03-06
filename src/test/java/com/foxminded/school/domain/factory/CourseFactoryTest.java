package com.foxminded.school.domain.factory;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.domain.models.Course;

class CourseFactoryTest {

    private CourseGenerator courseGenerator;
    
    @BeforeEach
    void init() {
        courseGenerator = new CourseGenerator();
    }
    
    @Test
    void testGenerate() {
        List<Course> actual = courseGenerator.generate(10);
        assertEquals(10, actual.size());
    }
}
