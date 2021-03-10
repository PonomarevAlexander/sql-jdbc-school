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
    private static final String TEST_CONFIG_FILE = "src\\test\\resources\\test_db_config.txt";
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
    private static final String STUDENT_NAME_1 = "alex";
    private static final String STUDENT_NAME_2 = "fill";
    private static final String STUDENT_NAME_3 = "bill";
    private static final String STUDENT_NAME_4 = "chuck";
    private static final String STUDENT_NAME_5 = "lex";
    private static final String STUDENT_NAME_6 = "john";
    private static final String GROUP_NAME_1 = "aa--00";
    private static final String GROUP_NAME_2 = "bb--11";
    private static final String COURSE_NAME_1 = "math";
    private static final String COURSE_NAME_2 = "biology";
    private static final String COURSE_NAME_3 = "art";
    
    @BeforeEach
    void setup() throws DaoException {
        config = new DBConfig(TEST_CONFIG_FILE);
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
    void testShowMainMenu() {
        String actual = menu.showMainMenu();
        assertEquals(EXPECTED_MENU, actual);
    }
    
    @Test
    void testResultMenuEngineSelect1() {
        String actual = menu.menuEngine(1);
        assertEquals(GROUP_NAME_1, actual);
    }
}