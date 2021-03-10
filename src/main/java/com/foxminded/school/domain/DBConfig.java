package com.foxminded.school.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBConfig {
    
    private String url;
    private String user;
    private String password;

    public DBConfig(String config) {
        setConfiguration(config);
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    private void setConfiguration(String config) {
        List<String> data = new ArrayList<>();
        try (Stream<String> fileStream = Files.lines(Paths.get(config))) {
            data = fileStream.limit(3).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.url = data.get(0);
        this.user = data.get(1);
        this.password = data.get(2);
    }
}
