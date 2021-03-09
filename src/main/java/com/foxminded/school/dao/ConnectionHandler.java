package com.foxminded.school.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {
    
    private String url;
    private String user;
    private String password;
    
    public ConnectionHandler(String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.password = pwd;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();            
        }
        return connection;
    }
}
