package com.foxminded.school.domain.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.foxminded.school.dao.DaoException;
import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.domain.DBConfig;
import com.foxminded.school.domain.models.Group;

public class GroupService implements Service<Group> {
    
    private GroupDao groupDao;

    public GroupService(DBConfig config) {
        groupDao = new GroupDao(config);
    }
    
    @Override
    public void add(Group group) {
        try {
            groupDao.add(group);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Group getById(int id) {
        Group group = new Group();
        try {
            group = groupDao.getById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return group;
    }
    
    @Override
    public void update(Group group) {
        try {
            groupDao.update(group);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void remove(Group group) {
        try {
            groupDao.remove(group);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void remove(int id) {
        try {
            groupDao.remove(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> getAll() {
        List<Group> groupsList = new ArrayList<>();
        try {
            groupsList = groupDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return groupsList;
    }
    
    public String getMinStudentCountIntoGroups() {
        String result = null;
        try {
            Map<String, Integer> groups = groupDao.getCountStudentsIntoGroups();
            result = Collections.min(groups.entrySet(), Comparator.comparingInt(Entry::getValue)).getKey();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }
}
