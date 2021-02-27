package com.foxminded.school.domain.services;

public interface Service<T, E> {
    
    void add(T entity);
    
    E getAll();
    
    T getById(int id);
    
    void edit(T entity);
    
    void remove(T entity);
    
    void remove(int id);
}
