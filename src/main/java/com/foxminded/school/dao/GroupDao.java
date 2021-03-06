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
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Group;

public class GroupDao implements Dao<Group> {

    private ConnectionHandler handler;
    private static final String QUERY_INSERT_GROUP_NAME = "INSERT into groups(group_name) values(?)";
    private static final String QUERY_SELECT_ALL = "SELECT * from groups";
    private static final String QUERY_SELECT_BY_ID = "SELECT * from groups where group_id = ?";
    private static final String QUERY_UPDATE_NAME = "UPDATE groups SET group_name = ? WHERE group_id = ?";
    private static final String QUERY_DELETE_BY_ID = "DELETE FROM groups WHERE group_id = ?";
    private static final String QUERY_STUDENTS_COUNT_IN_GROUP = "SELECT group_name, COUNT(s.group_id) AS count FROM students AS s JOIN groups USING(group_id) GROUP BY group_name";
    private static final String COLUMN_GROUP_NAME = "group_name";
    private static final String COLUMN_GROUP_ID = "group_id";
    private static final String COLUMN_COUNT = "count";
    private static final String EXCEPTION_ADD_GROUP = "adding failure";
    private static final String EXCEPTION_GET_ALL = "getting all failure";
    private static final String EXCEPTION_GET_BY_ID = "getting by id failure";
    private static final String EXCEPTION_UPDATE = "updating fail";
    private static final String EXCEPTION_REMOVE = "removing fail";
    private static final String EXCEPTION_COUNTING = "counting students in group fail";
   
    public GroupDao(DBConfig config) {
        this.handler = new ConnectionHandler(config.getUrl(), config.getUser(), config.getPassword());
    }
    
    @Override
    public void add(Group entity) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_GROUP_NAME)) {
            statement.setString(1, entity.getGroupName());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_ADD_GROUP, e);
        }
    }

    @Override
    public List<Group> getAll() throws DaoException {
        List<Group> groupsList = new ArrayList<>();
        try (Connection connection = handler.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL)) {
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setGroupName(resultSet.getString(COLUMN_GROUP_NAME));
                    group.setGroupID(resultSet.getInt(COLUMN_GROUP_ID));
                    groupsList.add(group);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GET_ALL, e);
        }
        return groupsList;
    }

    @Override
    public Group getById(int id) throws DaoException {
        Group group = new Group();
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    group.setGroupName(resultSet.getString(COLUMN_GROUP_NAME));
                    group.setGroupID(resultSet.getInt(COLUMN_GROUP_ID));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_GET_BY_ID, e);
        }
        return group;
    }

    @Override
    public void update(Group entity) throws DaoException {
        
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_NAME)) {
            statement.setString(1, entity.getGroupName());
            statement.setInt(2, entity.getGroupID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_UPDATE, e);
        }
    }

    @Override
    public void remove(Group entity) throws DaoException {
        try (Connection connection = handler.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_ID)) {
            statement.setInt(1, entity.getGroupID());
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
    
    public Map<String, Integer> getCountStudentsIntoGroups() throws DaoException {
        Map<String, Integer> resultCount = new HashMap<>();
        try (Connection connection = handler.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(QUERY_STUDENTS_COUNT_IN_GROUP)) {
                while(resultSet.next()) {
                    resultCount.put(
                            resultSet.getString(COLUMN_GROUP_NAME),
                            resultSet.getInt(COLUMN_COUNT));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_COUNTING, e);
        }
        return resultCount;
    }
}