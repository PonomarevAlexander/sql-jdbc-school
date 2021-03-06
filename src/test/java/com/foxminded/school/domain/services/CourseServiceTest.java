package com.foxminded.school.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.foxminded.school.dao.ConnectionHandler;
import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.DaoException;
import com.foxminded.school.dao.Runner;
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Course;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    private CourseService courseService;
    private Runner runner;
    private DBConfig config = new DBConfig(URL, USER, PASSWORD);
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "alex";
    private static final String PASSWORD = "";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    private static final String NAME_1 = "math";
    private static final String NAME_2 = "history";
    private static final String NAME_3 = "biology";
    
    @Mock
    CourseDao mockedDao;
    
    @Mock
    ConnectionHandler mockedHandler;
    
    @Mock
    Course mockedCourse;
    
    @InjectMocks
    CourseService mockedService = new CourseService(config);

    @BeforeEach
    void init() {
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
        courseService.edit(course);
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
    
    @Test
    void testAddTimesInvoke() {
        mockedService.add(mockedCourse);
        try {
            verify(mockedDao).add(mockedCourse);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testGetAllTimesInvoke() {
        mockedService.getAll();
        try {
            verify(mockedDao).getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testGetByIdTimesInvoke() {
        mockedService.getById(anyInt());
        try {
            verify(mockedDao).getById(anyInt());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testEditTimesInvoke() {
        mockedService.edit(mockedCourse);
        try {
            verify(mockedDao).update(mockedCourse);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testRemoveByIdTimesInvoke() {
        mockedService.remove(anyInt());
        try {
            verify(mockedDao).remove(anyInt());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testRemoveByEntityTimesInvoke() {
        mockedService.remove(mockedCourse);
        try {
            verify(mockedDao).remove(mockedCourse);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testGetByNameTimesInvoke() {
        mockedService.getbyName(anyString());
        try {
            verify(mockedDao).getByName(anyString());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
}
