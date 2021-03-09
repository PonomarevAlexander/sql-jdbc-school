package com.foxminded.school.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

import com.foxminded.school.domain.DBConfig;

public class Runner {
    
    private ConnectionHandler handler;

    public Runner(DBConfig config) {
        this.handler = new ConnectionHandler(config.getUrl(), config.getUser(), config.getPassword());
    }

    public void executeScript(String url) {
        try (Connection connection = handler.getConnection()) {
            ScriptRunner runner = new ScriptRunner(connection);
            try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
                runner.runScript(reader);
            } 
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
