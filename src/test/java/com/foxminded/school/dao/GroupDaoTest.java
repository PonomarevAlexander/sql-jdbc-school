package com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Group;
import com.foxminded.school.domain.models.Student;

class GroupDaoTest {
    
    private GroupDao groupDao;
    private StudentDao studentDao;
    private DBConfig config;
    private static Runner runner;
    private static final Path testCfgFile = Paths.get("src\\test\\resources\\test_db_config.txt");
    private static final String GROUP_NAME_1 = "aa--00";
    private static final String GROUP_NAME_2 = "bb--11";
    private static final String GROUP_NAME_3 = "cc--22";
    private static final String CREATE_TABLES = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP_TABLES = "src\\main\\resources\\drop_tables.sql";
    
    @BeforeEach
    void init() throws DaoException {
        config = new DBConfig(testCfgFile);
        studentDao = new StudentDao(config);
        groupDao = new GroupDao(config);
        runner = new Runner(config);
        runner.executeScript(DROP_TABLES);
        runner.executeScript(CREATE_TABLES);
        List<Student> students = Arrays.asList(
                new Student(1,"fizz", "fizz", 1, new HashSet<Integer>(Arrays.asList(1,2,3))),
                new Student(2,"bazz", "bazz", 1, new HashSet<Integer>(Arrays.asList(1,2))),
                new Student(3,"bar", "bar", 2, new HashSet<Integer>(Arrays.asList(1))),
                new Student(4,"foo", "foo", 2, new HashSet<Integer>(Arrays.asList(1,3))),
                new Student(5,"mazz", "mazz", 2, new HashSet<Integer>(Arrays.asList(2,3))));
        students.forEach(student -> {
            try {
                studentDao.add(student);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
        groupDao.add(new Group(GROUP_NAME_1));
        groupDao.add(new Group(GROUP_NAME_2));
        
    }

    @Test
    void testAdd() throws DaoException {
        groupDao.add(new Group(GROUP_NAME_3));
        Group actual = groupDao.getById(3);
        assertEquals(GROUP_NAME_3, actual.getGroupName());
    }
    
    @Test
    void testGetAll() throws DaoException {
        List<Group> groupList = groupDao.getAll();
        assertEquals(GROUP_NAME_1, groupList.get(0).getGroupName());
        assertEquals(1, groupList.get(0).getGroupID());
        assertEquals(GROUP_NAME_2, groupList.get(1).getGroupName());
        assertEquals(2, groupList.get(1).getGroupID());
        assertEquals(2, groupList.size());
    }
    
    @Test
    void testGetById() throws DaoException {
        Group actual = groupDao.getById(1);
        assertEquals(GROUP_NAME_1, actual.getGroupName());
        assertEquals(1, actual.getGroupID());
    }
    
    @Test
    void testUpdate() throws DaoException {
        Group editGroup = groupDao.getById(1);
        editGroup.setGroupName(GROUP_NAME_3);
        groupDao.update(editGroup);
        Group actual = groupDao.getById(1);
        assertEquals(GROUP_NAME_3, actual.getGroupName());
        
    }
    
    @Test
    void testRemoveById() throws DaoException {
        List<Group> initial = groupDao.getAll();
        groupDao.remove(1);
        List<Group> actual = groupDao.getAll();
        assertEquals(2, initial.size());
        assertEquals(1, actual.size());
    }
    
    @Test
    void testRemoveByEntity() throws DaoException {
        List<Group> initial = groupDao.getAll();
        Group group =  new Group(GROUP_NAME_1);
        group.setGroupID(1);
        groupDao.remove(group);
        List<Group> actual = groupDao.getAll();
        assertEquals(2, initial.size());
        assertEquals(1, actual.size());
    }
    @Test
    void testGetCountStudentIntoGroups() throws DaoException {
        Map<String, Integer> actual = groupDao.getCountStudentsIntoGroups();
        assertEquals(2, actual.get(GROUP_NAME_1));
        assertEquals(3, actual.get(GROUP_NAME_2));
    }
}