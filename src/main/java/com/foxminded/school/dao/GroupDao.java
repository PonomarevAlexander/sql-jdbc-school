package com.foxminded.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.foxminded.school.domain.DBConfigDto;
import com.foxminded.school.domain.models.Group;

public class GroupDao implements Dao<Group, List<Group>> {

    private ConnectionHandler handler;
    private static final String QUERY_INSERT_GROUP_NAME = "INSERT into groups(group_name) values(?)";
    private static final String QUERY_SELECT_ALL = "SELECT * from groups";
    private static final String QUERY_SELECT_BY_ID = "SELECT * from groups where group_id = ?";
    private static final String QUERY_UPDATE_NAME = "UPDATE groups SET group_name = ? WHERE group_id = ?";
    private static final String QUERY_DELETE_BY_ID = "DELETE FROM groups WHERE group_id = ?";
    private static final String QUERY_STUDENTS_COUNT_IN_GROUP = "SELECT group_name, COUNT(group_id) FROM students JOIN groups USING(group_id) GROUP BY group_name";
    private static final String COLUMN_GROUP_NAME = "group_name";
    private static final String COLUMN_GROUP_ID = "group_id";
    private static final String COLUMN_COUNT = "count";
    private static final String EXCEPTION_ADD_GROUP = "adding failure";
    private static final String EXCEPTION_GET_ALL = "getting all failure";
    private static final String EXCEPTION_GET_BY_ID = "getting by id failure";
    private static final String EXCEPTION_UPDATE = "updating fail";
    private static final String EXCEPTION_REMOVE = "removing fail";
    private static final String EXCEPTION_COUNTING = "counting students in group fail";
   
    public GroupDao(DBConfigDto config) {
        this.handler = new ConnectionHandler(config.getUrl(), config.getUser(), config.getPassword());
    }
    
    @Override
    public void add(Group entity) throws DaoException {
        Connection connection = handler.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_GROUP_NAME)) {
            statement.setString(1, entity.getGroupName());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_ADD_GROUP, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
    }

    @Override
    public List<Group> getAll() throws DaoException {
        Connection connection = handler.getConnection();
        ResultSet resultSet = null;
        List<Group> groupsList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(QUERY_SELECT_ALL);
            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupName(resultSet.getString(COLUMN_GROUP_NAME));
                group.setGroupID(resultSet.getInt(COLUMN_GROUP_ID));
                groupsList.add(group);
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GET_ALL, e);
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
        return groupsList;
    }

    @Override
    public Group getById(int id) throws DaoException {
        Connection connection = handler.getConnection();
        ResultSet resultSet = null;
        Group group = new Group();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                group.setGroupName(resultSet.getString(COLUMN_GROUP_NAME));
                group.setGroupID(resultSet.getInt(COLUMN_GROUP_ID));
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GET_BY_ID, e);
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
        return group;
    }

    @Override
    public void update(Group entity) throws DaoException {
        Connection connection = handler.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_NAME)) {
            statement.setString(1, entity.getGroupName());
            statement.setInt(2, entity.getGroupID());
            statement.executeUpdate();
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
    public void remove(Group entity) throws DaoException {
        Connection connection = handler.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_ID)) {
            statement.setInt(1, entity.getGroupID());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }
    }
    
    @Override
    public void remove(int id) throws DaoException {
        Connection connection = handler.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_REMOVE, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }
    }
    
    public Map<String, Integer> getCountStudentsIntoGroups() throws DaoException {
        Connection connection = handler.getConnection();
        ResultSet resultSet = null;
        Map<String, Integer> resultCount = new HashMap<>();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(QUERY_STUDENTS_COUNT_IN_GROUP);
            while(resultSet.next()) {
                resultCount.put(
                        resultSet.getString(COLUMN_GROUP_NAME),
                        resultSet.getInt(COLUMN_COUNT));
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_COUNTING, e);
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
        return resultCount;
    }
    
}