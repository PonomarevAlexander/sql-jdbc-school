package com.foxminded.school.domain.services;

import java.util.ArrayList;
import java.util.List;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.DaoException;
import com.foxminded.school.domain.models.Course;

public class CourseService implements Service<Course, List<Course>> {
    
    CourseDao courseDao = new CourseDao();

    @Override
    public void add(Course entity) {
        try {
            courseDao.add(entity);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAll() {
        List<Course> coursesList = new ArrayList<>();
        try {
            coursesList = courseDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return coursesList;
    }

    @Override
    public Course get(int id) {
        Course course = new Course();
        try {
            course = courseDao.getById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return course;
    }

    @Override
    public void edit(Course entity) {
        try {
            courseDao.update(entity);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Course entity) {
        try {
            courseDao.remove(entity);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        try {
            courseDao.remove(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    public Course getbyName(String courseName) {
        Course course = new Course();
        try {
            course = courseDao.getByName(courseName);
        } catch (DaoException e) {
            e.getStackTrace();
        }
        return course;
    }

}
