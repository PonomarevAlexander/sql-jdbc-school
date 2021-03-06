package com.foxminded.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Course;

public class CourseDao implements Dao<Course> {

    private ConnectionHandler handler;
    private static final String QUERY_INSERT = "INSERT INTO courses(course_name, course_description) VALUES(?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM courses";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
    private static final String QUERY_UPDATE_COURSE = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String QUERY_DELETE = "DELETE FROM courses WHERE course_id = ?";
    private static final String QUERY_SELECT_BY_NAME = "SELECT * FROM courses WHERE course_name = ?";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_COURSE_DESCRIPTION = "course_description";
    private static final String COLUMN_COURSE_ID = "course_id";
    private static final String EXCEPTION_ADD_COURSE = "adding failure";
    private static final String EXCEPTION_GETTING = "getting fail";
    private static final String EXCEPTION_UPDATE = "updating failure";
    private static final String EXCEPTION_REMOVE = "removing fail";
    
    public CourseDao(DBConfig config) {
        this.handler = new ConnectionHandler(config.getUrl(), config.getUser(), config.getPassword());
    }
    
    @Override
    public void add(Course entity) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            statement.setString(1, entity.getCourseName());
            statement.setString(2, entity.getCourseDescription());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_ADD_COURSE, e);
        }
    }

    @Override
    public List<Course> getAll() throws DaoException {
        List<Course> coursesList = new ArrayList<>();
        try (Connection connection = handler.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL)) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setCourseName(resultSet.getString(COLUMN_COURSE_NAME));
                    course.setCourseDescription(resultSet.getString(COLUMN_COURSE_DESCRIPTION));
                    course.setCourseID(resultSet.getInt(COLUMN_COURSE_ID));
                    coursesList.add(course);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GETTING, e);
        } 
        return coursesList;
    }

    @Override
    public Course getById(int id) throws DaoException {
        Course course = new Course();
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    course.setCourseName(resultSet.getString(COLUMN_COURSE_NAME));
                    course.setCourseDescription(resultSet.getString(COLUMN_COURSE_DESCRIPTION));
                    course.setCourseID(resultSet.getInt(COLUMN_COURSE_ID));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GETTING);
        } 
        return course;
    }
    
    public Course getByName(String courseName) throws DaoException {
        Course course = new Course();
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_NAME)) {
            statement.setString(1, courseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    course.setCourseID(resultSet.getInt(COLUMN_COURSE_ID));
                    course.setCourseName(resultSet.getString(COLUMN_COURSE_NAME));
                    course.setCourseDescription(resultSet.getString(COLUMN_COURSE_DESCRIPTION));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GETTING, e);
        }
        return course;
    }

    @Override
    public void update(Course entity) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_COURSE)) {
            statement.setString(1, entity.getCourseName());
            statement.setString(2, entity.getCourseDescription());
            statement.setInt(3, entity.getCourseID());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_UPDATE, e);
        }
    }

    @Override
    public void remove(Course entity) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
            statement.setInt(1, entity.getCourseID());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE, e);
        }
    }

    @Override
    public void remove(int id) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE, e);
        }
    }
}
