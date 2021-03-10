package com.foxminded.school.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.dao.Runner;
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Group;

class GroupServiceTest {

    private GroupService groupService;
    private DBConfig config;
    private Runner runner;
    private static final String TEST_CONFIG_FILE = "src\\test\\resources\\test_db_config.txt";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    private static final String GROUP_NAME_1 = "aa--00";
    private static final String GROUP_NAME_2 = "bb--11";
    private static final String GROUP_NAME_3 = "cc--22";
    
    @BeforeEach
    void init() {
        config = new DBConfig(TEST_CONFIG_FILE);
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
        groupService = new GroupService(config);
        groupService.add(new Group(GROUP_NAME_1));
        groupService.add(new Group(GROUP_NAME_2));
        
    }

    @Test
    void testAdd() {
        Group actual = new Group();
        groupService.add(new Group(GROUP_NAME_3));
        actual = groupService.getById(3);
        assertEquals(GROUP_NAME_3, actual.getGroupName());
        assertEquals(3, actual.getGroupID());
    }
    
    @Test
    void testGetById() {
        Group actual = new Group();
        actual = groupService.getById(2);
        assertEquals(GROUP_NAME_2, actual.getGroupName());
        assertEquals(2, actual.getGroupID());
    }
    
    @Test
    void testEdit() {
        Group group = groupService.getById(1);
        group.setGroupName(GROUP_NAME_3);
        groupService.update(group);
        Group newGroup = groupService.getById(1);
        
        assertEquals(1, newGroup.getGroupID());
        assertEquals(GROUP_NAME_3, newGroup.getGroupName());
    }
    
    @Test
    void testRemoveById() {
        List<Group> beforeRemovingList = groupService.getAll();
        groupService.remove(2);
        List<Group> afterRemovingList = groupService.getAll();
        
        assertEquals(2, beforeRemovingList.size());
        assertEquals(1, afterRemovingList.size());
    }
    
    @Test
    void testRemoveByEntity() {
        List<Group> beforeRemovingList = groupService.getAll();
        Group group = groupService.getById(1);
        groupService.remove(group);
        List<Group> afterRemovingList = groupService.getAll();
        
        assertEquals(2, beforeRemovingList.size());
        assertEquals(1, afterRemovingList.size());
    }
    
    @Test
    void testGetAll() {
        List<Group> expectedList = Arrays.asList(new Group(GROUP_NAME_1), new Group(GROUP_NAME_2));
        List<Group> actualList = groupService.getAll();
        
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getGroupName(), actualList.get(0).getGroupName());
        assertEquals(expectedList.get(1).getGroupName(), actualList.get(1).getGroupName());
    }
}