package com.foxminded.school.domain.services;

import java.util.List;

public interface Service<T> {
    
    void add(T entity);
    
    List<T> getAll();
    
    T getById(int id);
    
    void update(T entity);
    
    void remove(T entity);
    
    void remove(int id);
}
