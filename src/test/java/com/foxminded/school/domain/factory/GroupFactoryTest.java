package com.foxminded.school.domain.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.school.domain.models.Group;

class GroupFactoryTest {

    GroupFactory gFactory;
    
    @BeforeEach
    void init() {
        gFactory = new GroupFactory();
    }
    
    @Test
    void testGenerate() {
        List<Group> groups = gFactory.generate(10);
        assertEquals(10, groups.size());
    }
}
