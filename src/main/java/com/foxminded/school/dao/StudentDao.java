package com.foxminded.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Student;

public class StudentDao implements Dao<Student> {

    private ConnectionHandler handler;
    private static final String QUERY_INSERT_STUDENT = "INSERT INTO students(first_name, last_name, group_id) values(?, ?, ?)";
    private static final String QUERY_INSERT_COURSE = "INSERT INTO students_courses(student_id, course_id) VALUES(?, ?)";
    private static final String QUERY_SELECT_ALL_STUDENTS = "SELECT * FROM students";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM STUDENTS WHERE student_id = ?";
    private static final String QUERY_SELECT_STUDENTS_BY_COURSE = "SELECT first_name, last_name FROM students JOIN students_courses USING(student_id) WHERE course_id = ?";
    private static final String QUERY_SELECT_STUDENT_COURSES = "SELECT course_id FROM students_courses WHERE student_id = ?";
    private static final String QUERY_DELETE_BY_ID = "DELETE FROM students WHERE student_id = ?";
    private static final String QUERY_DELETE_BY_FULL_NAME = "DELETE FROM students WHERE first_name = ? AND last_name = ?";
    private static final String QUERY_DELETE_FROM_COURSE = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
    private static final String QUERY_UPDATE_STUDENT = "UPDATE students SET first_name = ?, last_name = ?, group_id = ? WHERE student_id = ?";
    private static final String EXCEPTION_ADDING_FAIL = "student adding fail";
    private static final String EXCEPTION_GET_ALL = "getting all students fail";
    private static final String EXCEPTION_GET_BY_ID = "geting student by id fail";
    private static final String EXCEPTION_UPDATE = "student updating failure";
    private static final String EXCEPTION_REMOVE = "removing student fail";
    private static final String EXCEPTION_ADD_COURSE = "course insert fail";
    private static final String EXCEPTION_REMOVE_COURSE = "remove course fail";
    private static final String EXCEPTION_GET_COURSES = "get student courses fail";
    private static final String EXCEPTION_GET_STUDENTS_FROM_COURSE = "getting students from course fail";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_GROUP_ID = "group_id";
    private static final String COLUMN_COURSE_ID = "course_id";

    public StudentDao(DBConfig config) {
        this.handler = new ConnectionHandler(config.getUrl(), config.getUser(), config.getPassword());
    }
    
    @Override
    public void add(Student entity) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_STUDENT)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setInt(3, entity.getGroupId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_ADDING_FAIL, e);
        }
    }

    @Override
    public List<Student> getAll() throws DaoException {
        List<Student> studentsList = new ArrayList<>();
        try (Connection connection = handler.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL_STUDENTS)) {
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
                    student.setLastName(resultSet.getString(COLUMN_LAST_NAME));
                    student.setStudentID(resultSet.getInt(COLUMN_STUDENT_ID));
                    student.setGroupId(resultSet.getInt(COLUMN_GROUP_ID));
                    studentsList.add(student);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GET_ALL, e);
        }
        return studentsList;
    }

    @Override
    public Student getById(int id) throws DaoException {
        Student student = new Student();
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    student.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
                    student.setLastName(resultSet.getString(COLUMN_LAST_NAME));
                    student.setStudentID(resultSet.getInt(COLUMN_STUDENT_ID));
                    student.setGroupId(resultSet.getInt(COLUMN_GROUP_ID));
                    student.setCourses(getStudentCourses(id));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GET_BY_ID, e);
        }
        return student;
    }

    @Override
    public void update(Student entity) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_STUDENT)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setInt(3, entity.getGroupId());
            statement.setInt(4, entity.getStudentID());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_UPDATE, e);
        }
        for (Integer course : entity.getCourses()) {
            try (Connection connection = handler.getConnection();
                    PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_COURSE)) {
                statement.setInt(1, entity.getStudentID());
                statement.setInt(2, course);
                statement.execute();
            } catch (SQLException e) {
                throw new DaoException(EXCEPTION_UPDATE, e);
            }
        }
    }

    @Override
    public void remove(Student entity) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_ID)) {
            statement.setInt(1, entity.getStudentID());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE, e);
        }
    }

    @Override
    public void remove(int id) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE, e);
        }
    }

    public void remove(String name, String lastName) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_FULL_NAME)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE, e);
        }
    }

    public List<Student> getStudentsWithGivenCourse(Course course) throws DaoException {
        List<Student> resultList = new ArrayList<>();
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_STUDENTS_BY_COURSE)) {
            statement.setInt(1, course.getCourseID());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Student student = new Student(resultSet.getString(COLUMN_FIRST_NAME),
                            resultSet.getString(COLUMN_LAST_NAME));
                    resultList.add(student);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GET_STUDENTS_FROM_COURSE, e);
        }
        return resultList;
    }

    public void addCourseSet(Student student) throws DaoException {
        int id = student.getStudentID();
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_COURSE)) {
            for (Integer courseId : student.getCourses()) {
                statement.setInt(1, id);
                statement.setInt(2, courseId);
                statement.execute();
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_ADD_COURSE, e);
        }
    }

    public void removeFromCourse(int studentId, int courseId) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_FROM_COURSE)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE_COURSE, e);
        }
    }

    public Set<Integer> getStudentCourses(int studentId) throws DaoException {
        Set<Integer> courses = new HashSet<>();
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_STUDENT_COURSES)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int courseId = resultSet.getInt(COLUMN_COURSE_ID);
                    courses.add(courseId);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GET_COURSES, e);
        }
        return courses;
    }
}