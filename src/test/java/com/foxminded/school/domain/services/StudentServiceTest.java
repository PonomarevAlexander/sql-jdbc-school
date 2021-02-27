package com.foxminded.school.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.dao.ConnectionHandler;
import com.foxminded.school.dao.Runner;
import com.foxminded.school.domain.models.Student;

class StudentServiceTest {

    StudentService studentService;
    private Runner runner;
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
    
    @BeforeEach
    void init() {
        runner = new Runner(new ConnectionHandler(URL, USER, PASSWORD));
        runner.executeScript(CREATE_TABLES);
        studentService = new StudentService(new ConnectionHandler(URL, USER, PASSWORD));
        studentService.add(NAME_1, LAST_NAME_1);
        studentService.add(NAME_2, LAST_NAME_2);
    }
    
    @AfterEach
    void deleteTables() {
        runner.executeScript(DROP_TABLES);
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
}