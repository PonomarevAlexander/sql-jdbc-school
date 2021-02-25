package com.foxminded.school.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

public class Runner {
    
    ConnectionHandler handler;

    public Runner(ConnectionHandler handler) {
        this.handler = handler;
    }

    public void executeScript(String url) {
        Connection connection = handler.getConnection();
        ScriptRunner runner = new ScriptRunner(connection);
        try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
            runner.runScript(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally { 
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.getStackTrace();
            }
        }
    }
}
