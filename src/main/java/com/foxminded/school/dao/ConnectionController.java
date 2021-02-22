package com.foxminded.school.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionController {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/school";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();            
        }
        return connection;
    }
}
