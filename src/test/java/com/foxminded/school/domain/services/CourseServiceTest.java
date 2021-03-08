package com.foxminded.school.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.dao.Runner;
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Course;

class CourseServiceTest {

    private CourseService courseService;
    private Runner runner;
    private DBConfig config;
    private static final Path testCfgFile = Paths.get("src\\test\\resources\\test_db_config.txt");
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    private static final String NAME_1 = "math";
    private static final String NAME_2 = "history";
    private static final String NAME_3 = "biology";

    @BeforeEach
    void init() {
        config = new DBConfig(testCfgFile);
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
        courseService = new CourseService(config);
        courseService.add(new Course(NAME_1));
        courseService.add(new Course(NAME_2));
    }

    @Test
    void testAdd() {
        List<Course> initialList = courseService.getAll();
        courseService.add(new Course(NAME_3));
        List<Course> actualList = courseService.getAll();
        
        assertEquals(2, initialList.size());
        assertEquals(3, actualList.size());
        assertEquals(NAME_3, courseService.getById(3).getCourseName());
    }
    
    @Test
    void testGetAll() {
        List<Course> actualList = courseService.getAll();
        assertEquals(2, actualList.size());
        assertEquals(NAME_1, actualList.get(0).getCourseName());
        assertEquals(NAME_2, actualList.get(1).getCourseName());
    }
    
    @Test
    void testGetById() {
        Course actual = courseService.getById(1);
        assertEquals(1, actual.getCourseID());
        assertEquals(NAME_1, actual.getCourseName());
    }
    
    @Test
    void testGetByCourseName() {
        Course actual = courseService.getbyName(NAME_2);
        assertEquals(2, actual.getCourseID());
        assertEquals(NAME_2, actual.getCourseName());
    }
    
    @Test
    void testEdit() {
        Course course = new Course(NAME_3);
        course.setCourseID(1);
        courseService.update(course);
        Course actual = courseService.getById(1);
        
        assertEquals(NAME_3, actual.getCourseName());
        assertEquals(1, actual.getCourseID());
    }
    
    @Test
    void testRemoveById() {
        List<Course> initialList = courseService.getAll();
        courseService.remove(1);
        List<Course> actualList = courseService.getAll();
        
        assertEquals(2, initialList.size());
        assertEquals(1, actualList.size());
    }
    
    @Test
    void testRemoveByEntiy() {
        Course course = new Course(NAME_1);
        course.setCourseID(1);
        List<Course> initialList = courseService.getAll();
        courseService.remove(course);
        List<Course> actualList = courseService.getAll();
        
        assertEquals(2, initialList.size());
        assertEquals(1, actualList.size());
    }
}