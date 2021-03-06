package com.foxminded.school.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.foxminded.school.dao.ConnectionHandler;
import com.foxminded.school.dao.DaoException;
import com.foxminded.school.dao.Runner;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.domain.ConsoleFormatter;
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Student;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private StudentService studentService;
    private Runner runner;
    private DBConfig config = new DBConfig(URL, USER, PASSWORD);
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "alex";
    private static final String PASSWORD = "";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    private static final String NAME_1 = "dart";
    private static final String LAST_NAME_1 = "vader";
    private static final String NAME_2 = "sam";
    private static final String LAST_NAME_2 = "fisher";
    private static final String NAME_3 = "peter";
    private static final String LAST_NAME_3 = "parker";
    
    @Mock
    StudentDao mockedDao;
    
    @Mock
    ConnectionHandler mockedHandler;
    
    @Mock
    Student mockedStudent;
    
    @Mock
    Course mockedCourse;
    
    @Mock
    ConsoleFormatter mockedFormatter;
   
    @InjectMocks
    StudentService mockedService = new StudentService(config);
    
    
    @BeforeEach
    void init() {
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
        studentService = new StudentService(config);
        studentService.add(NAME_1, LAST_NAME_1);
        studentService.add(NAME_2, LAST_NAME_2);
    }

    @Test
    void testAdd() {
        studentService.add(new Student(NAME_3, LAST_NAME_3));
        Student student = studentService.getById(3);
        assertEquals(NAME_3, student.getFirstName());
        assertEquals(LAST_NAME_3, student.getLastName());
        assertEquals(3, student.getStudentID());
    }
    
    @Test
    void testGetAll() {
        List<Student> expectedList = Arrays.asList(new Student(NAME_1, LAST_NAME_1), new Student(NAME_2, LAST_NAME_2));
        List<Student> actualList = studentService.getAll();
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getFirstName(), actualList.get(0).getFirstName());
        assertEquals(expectedList.get(1).getFirstName(), actualList.get(1).getFirstName());
    }
    
    @Test
    void testGetById() {
        Student student = studentService.getById(2);
        assertEquals(NAME_2, student.getFirstName());
        assertEquals(LAST_NAME_2, student.getLastName());
        assertEquals(2, student.getStudentID());
    }
    
    @Test
    void testEdit() {
        Student student = studentService.getById(1);
        student.setFirstName(NAME_3);
        student.setLastName(LAST_NAME_3);
        studentService.edit(student);
        Student actual = studentService.getById(1);
        
        assertEquals(NAME_3, actual.getFirstName());
        assertEquals(LAST_NAME_3, actual.getLastName());
        assertEquals(1, actual.getStudentID());
    }
    
    @Test
    void testRemoveById() {
        List<Student> initialList = studentService.getAll();
        studentService.remove(2);
        List<Student> actualList = studentService.getAll();
        
        assertEquals(2, initialList.size());
        assertEquals(1, actualList.size());
    }
    
    @Test
    void testRemoveByEntity() {
        Student student = new Student(NAME_2, LAST_NAME_2);
        student.setStudentID(2);
        List<Student> initialList = studentService.getAll();
        studentService.remove(student);
        List<Student> actualList = studentService.getAll();
        
        assertEquals(2, initialList.size());
        assertEquals(1, actualList.size());
    }
    
    @Test
    void testAddTimeInvoke() {
        mockedService.add(mockedStudent);
        try {
            verify(mockedDao).add(mockedStudent);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testGetAllTimeInvoke() {
        mockedService.getAll();
        try {
            verify(mockedDao).getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testEditTimeInvoke() {
        mockedService.edit(mockedStudent);;
        try {
            verify(mockedDao).update(mockedStudent);;
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testRemoveByIdTimeInvoke() {
        mockedService.remove(anyInt());
        try {
            verify(mockedDao).remove(anyInt());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testRemoveByEntityTimeInvoke() {
        mockedService.remove(mockedStudent);;
        try {
            verify(mockedDao).remove(mockedStudent);;
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testRemoveByFullNameTimeInvoke() {
        mockedService.remove(anyString(), anyString());
        try {
            verify(mockedDao).remove(anyString(), anyString());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testGetStudentGivenCourseTimesInvoke() {
        mockedService.getStudentsFromGivenCourse(mockedCourse);
        try {
            verify(mockedDao).getStudentsWithGivenCourse(mockedCourse);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testAddCoursesTimesInvoke() {
        mockedService.addCourses(mockedStudent);
        try {
            verify(mockedDao).addCourseSet(mockedStudent);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testRemoveFromCourseTimesInvoke() {
        mockedService.removeFromCourse(mockedStudent, 1);
        try {
            verify(mockedDao).removeFromCourse(mockedStudent.getStudentID(), 1);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testGetStudentCourses() {
        mockedService.getStudentCourses(mockedStudent);
        try {
            verify(mockedStudent).getStudentID();
            verify(mockedDao).getStudentCourses(mockedStudent.getStudentID());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}