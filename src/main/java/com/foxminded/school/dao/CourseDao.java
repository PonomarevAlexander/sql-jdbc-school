package com.foxminded.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.foxminded.school.domain.models.Course;

public class CourseDao implements Dao<Course, List<Course>> {

    ConnectionController controller = new ConnectionController();
    
    private static final String QUERY_INSERT = "INSERT INTO courses(course_name, course_description) VALUES(?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM courses";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
    private static final String QUERY_UPDATE_COURSE = "UPDATE courses SET course_name = ?, course_description = ? WHERE student_id = ?";
    private static final String QUERY_DELETE = "DELETE FROM courses WHERE course_id = ?";
    private static final String QUERY_SELECT_BY_NAME = "SELECT * FROM courses WHERE course_name = ?";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_COURSE_DESCRIPTION = "course_description";
    private static final String COLUMN_COURSE_ID = "course_id";
    private static final String EXCEPTION_ADD_COURSE = "adding failure";
    private static final String EXCEPTION_GETTING = "getting fail";
    private static final String EXCEPTION_UPDATE = "updating failure";
    private static final String EXCEPTION_REMOVE = "removing fail";
    
    
    @Override
    public void add(Course entity) throws DaoException {
        Connection connection = controller.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            statement.setString(1, entity.getCourseName());
            statement.setString(2, entity.getCourseDescription());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_ADD_COURSE, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }
    }

    @Override
    public List<Course> getAll() throws DaoException {
        Connection connection = controller.getConnection();
        ResultSet resultSet = null;
        List<Course> coursesList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(QUERY_SELECT_ALL);
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseName(resultSet.getString(COLUMN_COURSE_NAME));
                course.setCourseDescription(resultSet.getString(COLUMN_COURSE_DESCRIPTION));
                course.setCourseID(resultSet.getInt(COLUMN_COURSE_ID));
                coursesList.add(course);
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GETTING, e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
        return coursesList;
    }

    @Override
    public Course getById(int id) throws DaoException {
        Connection connection = controller.getConnection();
        ResultSet resultSet = null;
        Course course = new Course();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                course.setCourseName(resultSet.getString(COLUMN_COURSE_NAME));
                course.setCourseDescription(resultSet.getString(COLUMN_COURSE_DESCRIPTION));
                course.setCourseID(resultSet.getInt(COLUMN_COURSE_ID));
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GETTING);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                connection.close();
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }
        return course;
    }
    
    public Course getByName(String courseName) throws DaoException {
        Connection connection = controller.getConnection();
        ResultSet resultSet = null;
        Course course = new Course();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_NAME)) {
            statement.setString(1, courseName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                course.setCourseID(resultSet.getInt(COLUMN_COURSE_ID));
                course.setCourseName(resultSet.getString(COLUMN_COURSE_NAME));
                course.setCourseDescription(resultSet.getString(COLUMN_COURSE_DESCRIPTION));
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GETTING, e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                connection.close();
            } catch (SQLException e1) {
                e1.getStackTrace();
            }
        }
        return course;
    }

    @Override
    public void update(Course entity) throws DaoException {
        Connection connection = controller.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_COURSE)) {
            statement.setString(1, entity.getCourseName());
            statement.setString(2, entity.getCourseDescription());
            statement.setInt(3, entity.getCourseID());
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_UPDATE, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void remove(Course entity) throws DaoException {
        Connection connection = controller.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
            statement.setInt(1, entity.getCourseID());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void remove(int id) throws DaoException {
        Connection connection = controller.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
