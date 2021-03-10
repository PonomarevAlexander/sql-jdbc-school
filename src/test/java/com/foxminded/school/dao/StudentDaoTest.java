package com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Group;
import com.foxminded.school.domain.models.Student;

class StudentDaoTest {

    private StudentDao studentDao;
    private GroupDao groupDao;
    private CourseDao courseDao;
    private DBConfig config;
    private Runner runner;
    private static final String TEST_CONFIG_FILE = "src\\test\\resources\\test_db_config.txt";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    private static final String STUDENT_NAME_1 = "alex";
    private static final String STUDENT_NAME_2 = "fill";
    private static final String STUDENT_NAME_3 = "bill";
    private static final String STUDENT_NAME_4 = "chuck";
    private static final String STUDENT_NAME_5 = "lex";
    private static final String STUDENT_NAME_6 = "john";
    private static final String STUDENT_NAME_7 = "gregory";
    private static final String GROUP_NAME_1 = "aa--00";
    private static final String GROUP_NAME_2 = "bb--11";
    private static final String COURSE_NAME_1 = "math";
    private static final String COURSE_NAME_2 = "biology";
    private static final String COURSE_NAME_3 = "art";
    
    @BeforeEach
    void init() throws DaoException {
        config = new DBConfig(TEST_CONFIG_FILE);
        studentDao = new StudentDao(config);
        groupDao = new GroupDao(config);
        courseDao = new CourseDao(config);
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
        List<Student> students = Arrays.asList(new Student(1, STUDENT_NAME_1, STUDENT_NAME_1, 1, new HashSet<Integer>(Arrays.asList(1,2,3))),
                new Student(2, STUDENT_NAME_2, STUDENT_NAME_2, 1, new HashSet<Integer>(Arrays.asList(1,2,3))),
                new Student(3, STUDENT_NAME_3, STUDENT_NAME_3, 1, new HashSet<Integer>(Arrays.asList(1,2))),
                new Student(4, STUDENT_NAME_4, STUDENT_NAME_4, 2, new HashSet<Integer>(Arrays.asList(1))),
                new Student(5, STUDENT_NAME_5, STUDENT_NAME_5, 2, new HashSet<Integer>(Arrays.asList(1,3))),
                new Student(6, STUDENT_NAME_6, STUDENT_NAME_6, 2, new HashSet<Integer>(Arrays.asList(2,3))));
        groupDao.add(new Group(GROUP_NAME_1));
        groupDao.add(new Group(GROUP_NAME_2));
        courseDao.add(new Course(COURSE_NAME_1));
        courseDao.add(new Course(COURSE_NAME_2));
        courseDao.add(new Course(COURSE_NAME_3));
        for (Student student : students) {
            studentDao.add(student);
            studentDao.update(student);
        }
    }
    
    @Test
    void testAdd() throws DaoException {
        studentDao.add(new Student(STUDENT_NAME_7, STUDENT_NAME_7));
        Student actual = studentDao.getById(7);
        assertEquals(STUDENT_NAME_7, actual.getFirstName());
        assertEquals(STUDENT_NAME_7, actual.getLastName());
        assertEquals(7, actual.getStudentID());
    }
    
    @Test
    void testGetAll() throws DaoException {
        List<Student> actual = studentDao.getAll();
        assertEquals(6, actual.size());
        assertEquals(STUDENT_NAME_4, actual.get(3).getFirstName());
    }
    
    @Test
    void testGetById() throws DaoException {
        Student actual = studentDao.getById(1);
        assertEquals(STUDENT_NAME_1, actual.getFirstName());
        assertEquals(STUDENT_NAME_1, actual.getLastName());
        assertEquals(1, actual.getStudentID());
    }
    
    @Test
    void testUpdate() throws DaoException {
        Student editStudent = studentDao.getById(1);
        editStudent.setFirstName(STUDENT_NAME_7);
        editStudent.setLastName(STUDENT_NAME_7);
        editStudent.setGroupId(2);
        studentDao.update(editStudent);
        Student actual = studentDao.getById(1);
        assertEquals(1, actual.getStudentID());
        assertEquals(STUDENT_NAME_7, actual.getFirstName());
        assertEquals(STUDENT_NAME_7, actual.getLastName());
        assertEquals(2, actual.getGroupId());
    }
     @Test
     void testRemoveById() throws DaoException {
        List<Student> initial = studentDao.getAll();
        studentDao.remove(2);
        List<Student> actual = studentDao.getAll();
        assertEquals(6, initial.size());
        assertEquals(5, actual.size());
     }
     
     @Test
     void testRemoveByEntity() throws DaoException {
         Student student = studentDao.getById(4);
         List<Student> initial = studentDao.getAll();
         studentDao.remove(student);
         List<Student> actual = studentDao.getAll();
         assertEquals(6, initial.size());
         assertEquals(5, actual.size());
     }
     
     @Test
     void testAddCourseSet() throws DaoException {
         Student initial = studentDao.getById(4);
         assertFalse(initial.getCourses().contains(2));
         initial.setCourses(new HashSet<Integer>(Arrays.asList(1, 2, 3)));
         studentDao.update(initial);
         Student actual = studentDao.getById(4);
         assertTrue(actual.getCourses().containsAll(new HashSet<Integer>(Arrays.asList(1, 2, 3))));
     }
     
     @Test
     void testGetStudentsGivenCourse() throws DaoException {
         Course course = courseDao.getById(1);
         List<Student> actual = studentDao.getStudentsWithGivenCourse(course);
         assertEquals(5, actual.size());
     }
     
     @Test
     void testRemoveFromCourse() throws DaoException {
         Student initial = studentDao.getById(1);
         assertTrue(initial.getCourses().containsAll(new HashSet<Integer>(Arrays.asList(1,2,3))));
         studentDao.removeFromCourse(initial.getStudentID(), 2);
         Student actual = studentDao.getById(1);
         assertTrue(!actual.getCourses().contains(2));
     }
}
