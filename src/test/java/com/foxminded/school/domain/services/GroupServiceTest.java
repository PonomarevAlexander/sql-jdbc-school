package com.foxminded.school.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.dao.ConnectionHandler;
import com.foxminded.school.dao.Runner;
import com.foxminded.school.domain.models.Group;

class GroupServiceTest {

    GroupService groupService;
    private Runner runner;
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "alex";
    private static final String PASSWORD = "";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    private static final String GROUP_NAME_1 = "aa--00";
    private static final String GROUP_NAME_2 = "bb--11";
    private static final String GROUP_NAME_3 = "cc--22";
    
    @BeforeEach
    void init() {
        runner = new Runner(new ConnectionHandler(URL, USER, PASSWORD));
        runner.executeScript(CREATE_TABLES);
        groupService = new GroupService(new ConnectionHandler(URL, USER, PASSWORD));
        groupService.add(new Group(GROUP_NAME_1));
        groupService.add(new Group(GROUP_NAME_2));
        
    }
    
    @AfterEach
    void deleteTables() {
        runner.executeScript(DROP_TABLES);
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
        groupService.edit(group);
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
