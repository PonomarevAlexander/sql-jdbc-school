package com.foxminded.school.dao;

public interface Dao<T, E> {
    
    void add(T entity) throws DaoException;
    
    E getAll() throws DaoException; 
    
    T getById(int id) throws DaoException;
    
    void update(T entity) throws DaoException;
    
    void remove(T entity) throws DaoException;
    
    void remove(int id) throws DaoException;
}
