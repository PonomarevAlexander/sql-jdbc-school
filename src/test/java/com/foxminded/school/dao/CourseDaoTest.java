package com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.school.domain.DBConfigDto;
import com.foxminded.school.domain.models.Course;

class CourseDaoTest {

    private CourseDao courseDao;
    private static Runner runner;
    private DBConfigDto config = new DBConfigDto(URL, USER, PASSWORD);
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "alex";
    private static final String PASSWORD = "";
    private static final String COURSE_NAME_1 = "math";
    private static final String COURSE_NAME_2 = "english";
    private static final String COURSE_NAME_3 = "biology";
    private static final String COURSE_TEST_DESCRIPTION = "test test test test test";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    
    @BeforeEach
    void init() {
        courseDao = new CourseDao(config);
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
        try {
            courseDao.add(new Course(COURSE_NAME_1));
            courseDao.add(new Course(COURSE_NAME_2));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAdd() {
        Course actual = new Course();
        try {
            courseDao.add(new Course(COURSE_NAME_3));
            actual = courseDao.getById(3);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(COURSE_NAME_3, actual.getCourseName());
    }
    
    @Test
    void testGetAll() {
        List<Course> actualList = new ArrayList<>();
        try {
            actualList = courseDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(COURSE_NAME_1, actualList.get(0).getCourseName());
        assertEquals(COURSE_NAME_2, actualList.get(1).getCourseName());
    }
    
    @Test
    void testGetById() {
        Course course = new Course();
        try {
            course = courseDao.getById(2);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(COURSE_NAME_2, course.getCourseName());
        assertEquals(2, course.getCourseID());
    }
    
    @Test
    void testGetByName() {
        Course course = new Course();
        try {
            course = courseDao.getByName(COURSE_NAME_1);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(COURSE_NAME_1, course.getCourseName());
        assertEquals(1, course.getCourseID());
    }
    
    @Test
    void testUpdate() {
        Course newCourse = new Course(COURSE_NAME_3);
        Course actual = new Course();
        newCourse.setCourseID(2);
        newCourse.setCourseDescription(COURSE_TEST_DESCRIPTION);
        try {
            courseDao.update(newCourse);
            actual = courseDao.getById(2);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(COURSE_NAME_3, actual.getCourseName());
        assertEquals(COURSE_TEST_DESCRIPTION, actual.getCourseDescription());
        assertEquals(2, actual.getCourseID());
    }
    
    @Test
    void testRemoveByEntity() {
        Course course = new Course(COURSE_NAME_1);
        course.setCourseID(1);
        List<Course> beforeRemoving = new ArrayList<>();
        List<Course> afterRemoving = new ArrayList<>();
        try {
            beforeRemoving = courseDao.getAll();
            courseDao.remove(course);
            afterRemoving = courseDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(2, beforeRemoving.size());
        assertEquals(1, afterRemoving.size());
    }
    
    @Test
    void testRemoveById() {
        List<Course> beforeRemoving = new ArrayList<>();
        List<Course> afterRemoving = new ArrayList<>();
        try {
            beforeRemoving = courseDao.getAll();
            courseDao.remove(2);
            afterRemoving = courseDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(2, beforeRemoving.size());
        assertEquals(1, afterRemoving.size());
    }
}
