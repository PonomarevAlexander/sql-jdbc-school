package com.foxminded.school.domain.generators;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.domain.models.Group;

class GroupGeneratorTest {

    GroupGenerator gFactory;
    
    @BeforeEach
    void init() {
        gFactory = new GroupGenerator();
    }
    
    @Test
    void testGenerate() {
        List<Group> groups = gFactory.generate(10);
        assertEquals(10, groups.size());
    }
}
