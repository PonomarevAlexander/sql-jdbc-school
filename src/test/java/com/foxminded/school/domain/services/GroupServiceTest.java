package com.foxminded.school.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.foxminded.school.dao.ConnectionHandler;
import com.foxminded.school.dao.DaoException;
import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.Runner;
import com.foxminded.school.domain.DBConfigDto;
import com.foxminded.school.domain.models.Group;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    private GroupService groupService;
    private DBConfigDto config = new DBConfigDto(URL, USER, PASSWORD);
    private Runner runner;
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "alex";
    private static final String PASSWORD = "";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    private static final String GROUP_NAME_1 = "aa--00";
    private static final String GROUP_NAME_2 = "bb--11";
    private static final String GROUP_NAME_3 = "cc--22";
    
    @Mock
    GroupDao mockedDao;
    
    @Mock
    ConnectionHandler mockedHandler;
    
    @Mock
    Group mockedGroup;
    
    @InjectMocks
    GroupService mockedService = new GroupService(config);
    
    @BeforeEach
    void init() {
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
    
    @Test
    void testAddTimesInvokation() {
        mockedService.add(mockedGroup);
        try {
            verify(mockedDao).add(mockedGroup);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testGetByIdTimesInvokation() {
        mockedService.getById(anyInt());
        try {
            verify(mockedDao).getById(anyInt());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testGetAllTimesInvokation() {
        mockedService.getAll();
        try {
            verify(mockedDao).getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testEditTimesInvokation() {
        mockedService.edit(mockedGroup);
        try {
            verify(mockedDao).update(mockedGroup);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testRemoveByIdTimesInvokation() {
        mockedService.remove(anyInt());
        try {
            verify(mockedDao).remove(anyInt());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testRemoveByEntityTimesInvokation() {
        mockedService.remove(mockedGroup);
        try {
            verify(mockedDao).remove(mockedGroup);;
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }  
}
