package com.foxminded.school.domain.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.school.domain.models.Student;

class StudentFactoryTest {

    private StudentFactory studFactory;
    
    @BeforeEach
    void init() {
        studFactory = new StudentFactory();
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
