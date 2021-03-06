package com.foxminded.school.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.DaoException;
import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.Runner;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Group;
import com.foxminded.school.domain.models.Student;
import com.foxminded.school.domain.services.CourseService;
import com.foxminded.school.domain.services.GroupService;
import com.foxminded.school.domain.services.StudentService;

class OptionMenuTest {
    
    private OptionMenu menu;
    private DBConfig config;
    private StudentDao studentDao;
    private GroupDao groupDao;
    private CourseDao courseDao;
    private static Runner runner;
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "alex";
    private static final String PASSWORD = "";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    private static final String EXPECTED_MENU = "------------------------------------------------------------\n"
            + "1 Find all groups with less or equals student count\n"
            + "2 Find all students related to course with given name\n"
            + "3 Add new student\n"
            + "4 Delete student by ID\n"
            + "5 Add a student to the course (from a list)\n"
            + "6 Remove the student from one of his or her course\n"
            + "------------------------------------------------------------";    
    
    @BeforeEach
    void setup() {
        config = new DBConfig(URL, USER, PASSWORD);
        studentDao = new StudentDao(config);
        groupDao = new GroupDao(config);
        courseDao = new CourseDao(config);
        StudentService studentService = new StudentService(config);
        CourseService courseService = new CourseService(config);
        GroupService groupService = new GroupService(config);
        menu = new OptionMenu(studentService, courseService, groupService);
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
        List<Group> groups = Arrays.asList(new Group("groupOne"), new Group("groupTwo"));
        List<Course> courses = Arrays.asList(new Course("math"), new Course("english"), new Course("science"));
        List<Student> students = Arrays.asList(
                new Student(1, "Ivan", "Ivanov", 1, new HashSet<Integer>(Arrays.asList(1,2,3))),
                new Student(2, "Ivan", "Sergeev", 1, new HashSet<Integer>(Arrays.asList(1,2))),
                new Student(3, "Sergey", "Sergeev", 2, new HashSet<Integer>(Arrays.asList(1))),
                new Student(4, "Sergey", "Ivanov", 2, new HashSet<Integer>(Arrays.asList(1,3))),
                new Student(5, "Alexey", "Alexeev", 1, new HashSet<Integer>(Arrays.asList(2,3))),
                new Student(6, "Dmitriy", "Dmitriev", 1, new HashSet<Integer>(Arrays.asList(2))));
        
        groups.forEach(group -> {
            try {
                groupDao.add(group);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
        
        courses.forEach(course -> {
            try {
                courseDao.add(course);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
        
        students.forEach(student -> {
            try {
                studentDao.add(student);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
        
        students.forEach(student -> {
            try {
                studentDao.addCourseSet(student);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
    }
    
    @Test
    void testShowMainMenu() {
        String actual = menu.showMainMenu();
        assertEquals(EXPECTED_MENU, actual);
    }
}

























