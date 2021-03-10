package com.foxminded.school.dao;

import java.util.List;

public interface Dao<T> {
    
    void add(T entity) throws DaoException;
    
    List<T> getAll() throws DaoException; 
    
    T getById(int id) throws DaoException;
    
    void update(T entity) throws DaoException;
    
    void remove(T entity) throws DaoException;
    
    void remove(int id) throws DaoException;
}
