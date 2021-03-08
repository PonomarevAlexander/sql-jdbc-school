package com.foxminded.school.domain.generators;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.school.domain.generators.StudentGenerator;
import com.foxminded.school.domain.models.Student;

class StudentGeneratorTest {

    private StudentGenerator studFactory;
    
    @BeforeEach
    void init() {
        studFactory = new StudentGenerator();
    }
    
    @Test
    void testGenerate() {
        List<Student> actual = studFactory.generate(200);
        actual.forEach(student -> {
            assertNotNull(student.getFirstName());
            assertNotNull(student.getLastName());
        });
        assertEquals(200, actual.size());
    }
}
