package com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Course;

class CourseDaoTest {
    
    private CourseDao courseDao;
    private DBConfig config;
    private Runner runner;
    private static final Path testCfgFile = Paths.get("src\\test\\resources\\test_db_config.txt");
    private static final String COURSE_NAME_1 = "math";
    private static final String COURSE_NAME_2 = "english";
    private static final String COURSE_NAME_3 = "biology";
    private static final String COURSE_TEST_DESCRIPTION = "test test test test test";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    
    @BeforeEach
    void init() throws DaoException {
        config = new DBConfig(testCfgFile);
        courseDao = new CourseDao(config);
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
        courseDao.add(new Course(COURSE_NAME_1));
        courseDao.add(new Course(COURSE_NAME_2));
    }

    @Test
    void testAdd() throws DaoException {
        Course actual = new Course();
        courseDao.add(new Course(COURSE_NAME_3));
        actual = courseDao.getById(3);
        assertEquals(COURSE_NAME_3, actual.getCourseName());
    }
    
    @Test
    void testGetAll() throws DaoException {
        List<Course> actualList = new ArrayList<>();
        actualList = courseDao.getAll();
        assertEquals(COURSE_NAME_1, actualList.get(0).getCourseName());
        assertEquals(COURSE_NAME_2, actualList.get(1).getCourseName());
    }
    
    @Test
    void testGetById() throws DaoException {
        Course course = new Course();
        course = courseDao.getById(2);
        assertEquals(COURSE_NAME_2, course.getCourseName());
        assertEquals(2, course.getCourseID());
    }
    
    @Test
    void testGetByName() throws DaoException {
        Course course = new Course();
        course = courseDao.getByName(COURSE_NAME_1);
        assertEquals(COURSE_NAME_1, course.getCourseName());
        assertEquals(1, course.getCourseID());
    }
    
    @Test
    void testUpdate() throws DaoException {
        Course newCourse = new Course(COURSE_NAME_3);
        Course actual = new Course();
        newCourse.setCourseID(2);
        newCourse.setCourseDescription(COURSE_TEST_DESCRIPTION);
        courseDao.update(newCourse);
        actual = courseDao.getById(2);
        assertEquals(COURSE_NAME_3, actual.getCourseName());
        assertEquals(COURSE_TEST_DESCRIPTION, actual.getCourseDescription());
        assertEquals(2, actual.getCourseID());
    }
    
    @Test
    void testRemoveByEntity() throws DaoException {
        Course course = new Course(COURSE_NAME_1);
        course.setCourseID(1);
        List<Course> beforeRemoving = new ArrayList<>();
        List<Course> afterRemoving = new ArrayList<>();
        beforeRemoving = courseDao.getAll();
        courseDao.remove(course);
        afterRemoving = courseDao.getAll();
        assertEquals(2, beforeRemoving.size());
        assertEquals(1, afterRemoving.size());
    }
    
    @Test
    void testRemoveById() throws DaoException {
        List<Course> beforeRemoving = new ArrayList<>();
        List<Course> afterRemoving = new ArrayList<>();
        beforeRemoving = courseDao.getAll();
        courseDao.remove(2);
        afterRemoving = courseDao.getAll();
        assertEquals(2, beforeRemoving.size());
        assertEquals(1, afterRemoving.size());
    }
}
