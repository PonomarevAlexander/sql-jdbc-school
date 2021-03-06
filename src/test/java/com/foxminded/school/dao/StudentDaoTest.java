
package com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Student;

class StudentDaoTest {

    private StudentDao studentDao;
    private CourseDao courseDao;
    private DBConfig config = new DBConfig(URL, USER, PASSWORD);
    private static Runner runner;
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "alex";
    private static final String PASSWORD = "";
    private static final String TEST_NAME_1 = "Foo";
    private static final String TEST_NAME_2 = "Bar";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    
    @BeforeEach
    void init() {
        studentDao = new StudentDao(config);
        courseDao = new CourseDao(config);
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
    }
    
    @Test
    void testAdd() {
        Student actual = new Student();
        try {
            studentDao.add(new Student(TEST_NAME_1, TEST_NAME_1));
            actual = studentDao.getById(1);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(TEST_NAME_1, actual.getFirstName());
        assertEquals(TEST_NAME_1, actual.getLastName());
    }
    
    @Test
    void testGetAll() {
        List<Student> actual = new ArrayList<>();
        List<Student> studentsList = Arrays.asList(
                new Student(TEST_NAME_1, TEST_NAME_1), 
                new Student(TEST_NAME_2, TEST_NAME_2));
        studentsList.forEach(student -> {
            try {
                studentDao.add(student);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
        
        try {
            actual = studentDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(2, studentsList.size());
        assertEquals(studentsList.get(0).getFirstName(), actual.get(0).getFirstName());
        assertEquals(studentsList.get(1).getFirstName(), actual.get(1).getFirstName());
        assertEquals(studentsList.get(0).getLastName(), actual.get(0).getLastName());
        assertEquals(studentsList.get(1).getLastName(), actual.get(1).getLastName());
    }
    
    @Test
    void testGetById() {
        Student actual = new Student();
        try {
            studentDao.add(new Student(TEST_NAME_1, TEST_NAME_2));
            actual = studentDao.getById(1);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(TEST_NAME_1, actual.getFirstName());
        assertEquals(TEST_NAME_2, actual.getLastName());
        assertEquals(1, actual.getStudentID());
    }
    
    @Test
    void testUpdate() {
        Student newStudent = new Student();
        Student actual = new Student();
        try {
            studentDao.add(new Student(TEST_NAME_1, TEST_NAME_1));
            actual = studentDao.getById(1);
            actual.setFirstName(TEST_NAME_2); 
            actual.setLastName(TEST_NAME_2);
            actual.setCourses(new HashSet<Integer>());
            actual.setGroupId(1);
            studentDao.update(actual);
            newStudent = studentDao.getById(1);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(TEST_NAME_2, newStudent.getFirstName());
        assertEquals(TEST_NAME_2, newStudent.getLastName());
        assertEquals(1, newStudent.getStudentID());
    }
     @Test
     void testRemoveById() {
         List<Student> initialStudentList = new ArrayList<>();
         List<Student> actualStudentList = new ArrayList<>();
         try {
            studentDao.add(new Student(TEST_NAME_1, TEST_NAME_1));
            studentDao.add(new Student(TEST_NAME_2, TEST_NAME_2));
            initialStudentList = studentDao.getAll();
            studentDao.remove(2);
            actualStudentList = studentDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
         assertEquals(2, initialStudentList.size());
         assertEquals(1, actualStudentList.size()); 
     }
     
     @Test
     void testRemoveByEntity() {
         List<Student> initialStudentList = new ArrayList<>();
         List<Student> actualStudentList = new ArrayList<>();
         try {
             studentDao.add(new Student(TEST_NAME_1, TEST_NAME_1));
             studentDao.add(new Student(TEST_NAME_2, TEST_NAME_2));
             initialStudentList = studentDao.getAll();
             studentDao.remove(initialStudentList.get(1));
             actualStudentList = studentDao.getAll();
         } catch (DaoException e) {
             e.printStackTrace();
         }
         assertEquals(2, initialStudentList.size());
         assertEquals(1, actualStudentList.size()); 
     }
     
     @Test
     void testAddCourseSet() {
         Student actual = new Student();
         Student student = new Student();
         Set<Integer> courses = new HashSet<>();
         courses.add(1);
         courses.add(2);
         courses.add(3);
         try {
            courseDao.add(new Course("foo"));
            courseDao.add(new Course("bar"));
            courseDao.add(new Course("baz"));
        } catch (DaoException e1) {
            e1.printStackTrace();
        }
         try {
             studentDao.add(new Student(TEST_NAME_1, TEST_NAME_2));
             actual = studentDao.getById(1);
             actual.setCourses(courses);
             studentDao.addCourseSet(actual);
             student = studentDao.getById(1);
         } catch (DaoException e) {
             e.printStackTrace();
         }
         assertEquals(TEST_NAME_1, student.getFirstName());
         assertEquals(TEST_NAME_2, student.getLastName());
         assertEquals(1, student.getStudentID());
         assertTrue(student.getCourses().containsAll(courses));
     }
}
