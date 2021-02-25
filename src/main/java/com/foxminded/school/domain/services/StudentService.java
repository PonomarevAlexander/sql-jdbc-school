package com.foxminded.school.domain.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.foxminded.school.dao.ConnectionHandler;
import com.foxminded.school.dao.DaoException;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.domain.ConsoleFormatter;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Student;

public class StudentService implements Service<Student, List<Student>> {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/school";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private StudentDao studentDao = new StudentDao(new ConnectionHandler(URL, USER, PASSWORD));

    @Override
    public void add(Student entity) {
        try {
            studentDao.add(entity);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    public void add(String name, String lastName) {
        try {
            studentDao.add(new Student(name, lastName));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAll() {
        try {
            return studentDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Student get(int id) {
        Student student = new Student();
        try {
            student = studentDao.getById(id);
            student.setCourses(getStudentCourses(student));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void edit(Student entity) {
        try {
            studentDao.update(entity);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Student entity) {
        try {
            studentDao.remove(entity);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try {
            studentDao.remove(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    public void remove(String name, String lastName) {
        try {
            studentDao.remove(name, lastName);
        } catch (DaoException e) {
            e.getStackTrace();
        }
    }
    
    public String getStudentsFromGivenCourse(Course course) {
        ConsoleFormatter formatter = new ConsoleFormatter();
        List<Student> studentsList = new ArrayList<>();
        try {
            studentsList = studentDao.getStudentsWithGivenCourse(course);
        } catch (DaoException e) {
            e.getStackTrace();
        }
        return formatter.formatFor2ndMenuItem(studentsList);
    }
    
    public void addCourses(Student student) {
        try {
            studentDao.addCourseSet(student);
        } catch (DaoException e) {
            e.getStackTrace();
        }
    }
    
    public void removeFromCourse(Student student, int courseId) {
        try {
            studentDao.removeFromCourse(student.getStudentID(), courseId);
        } catch (DaoException e) {
            e.getStackTrace();
        }
    }
    
    public Set<Integer> getStudentCourses(Student student) {
        
        Set<Integer> courses = new HashSet<>();
        try {
            courses = studentDao.getStudentCourses(student.getStudentID());
        } catch (DaoException e) {
            e.getStackTrace();
        }
        return courses;
    }
}
