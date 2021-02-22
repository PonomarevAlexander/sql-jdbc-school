package com.foxminded.school.dao;

@SuppressWarnings("serial")
public class DaoException extends Exception {
    
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }
    
    public DaoException(String message, Throwable ex) {
        super(message, ex);
    }

}
