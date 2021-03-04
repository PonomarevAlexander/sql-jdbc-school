package com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.domain.DBConfigDto;
import com.foxminded.school.domain.models.Group;

class GroupDaoTest {
    
    private GroupDao groupDao;
    private DBConfigDto config = new DBConfigDto(URL, USER, PASSWORD);
    private static Runner runner;
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "alex";
    private static final String PASSWORD = "";
    private static final String GROUP_NAME_1 = "aa--00";
    private static final String GROUP_NAME_2 = "bb--11";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    
    @BeforeEach
    void init() {
        groupDao = new GroupDao(config);
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
    }

    @Test
    void testAdd() {
        Group group = new Group(GROUP_NAME_1);
        Group actualdGroup = new Group();
        try {
            groupDao.add(group);
            actualdGroup = groupDao.getById(1);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(actualdGroup.getGroupName(), group.getGroupName());
    }
    
    @Test
    void testGetAll() {
        List<Group> groupList = new ArrayList<>();
        Group group1 = new Group(GROUP_NAME_1);
        Group group2 = new Group(GROUP_NAME_2);
        try {
            groupDao.add(group1);
            groupDao.add(group2);
            groupList = groupDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(GROUP_NAME_1, groupList.get(0).getGroupName());
        assertEquals(1, groupList.get(0).getGroupID());
        assertEquals(GROUP_NAME_2, groupList.get(1).getGroupName());
        assertEquals(2, groupList.get(1).getGroupID());
        assertEquals(2, groupList.size());
    }
    
    @Test
    void testGetById() {
        Group actual = new Group();
        try {
            groupDao.add(new Group(GROUP_NAME_1));
            actual = groupDao.getById(1);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(GROUP_NAME_1, actual.getGroupName());
        assertEquals(1, actual.getGroupID());
    }
    
    @Test
    void testUpdate() {
        Group newGroup = new Group(GROUP_NAME_2);
        Group actualGroup = new Group();
        newGroup.setGroupID(1);
        try {
            groupDao.add(new Group(GROUP_NAME_1));
            groupDao.update(newGroup);
            actualGroup = groupDao.getById(1);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(GROUP_NAME_2, actualGroup.getGroupName());
        assertEquals(1, actualGroup.getGroupID());
    }
    
    @Test
    void testRemoveById() {
        List<Group> groupsList = Arrays.asList(new Group(GROUP_NAME_1), new Group(GROUP_NAME_2));
        List<Group> updatedGroupsList = new ArrayList<>();
        groupsList.forEach(group -> {
            try {
                groupDao.add(group);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
        try {
            groupsList = groupDao.getAll();
            Group group = groupDao.getById(1);
            groupDao.remove(group);
            updatedGroupsList = groupDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(2, groupsList.size());
        assertEquals(1, updatedGroupsList.size());
    }
    
    @Test
    void testRemoveByEntity() {
        List<Group> groupsList = Arrays.asList(new Group(GROUP_NAME_1), new Group(GROUP_NAME_2));
        List<Group> updatedGroupsList = new ArrayList<>();
        groupsList.forEach(group -> {
            try {
                groupDao.add(group);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
        try {
            groupsList = groupDao.getAll();
            Group group = groupDao.getById(groupsList.get(0).getGroupID());
            groupDao.remove(group);
            updatedGroupsList = groupDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        assertEquals(2, groupsList.size());
        assertEquals(1, updatedGroupsList.size());
    }
}























