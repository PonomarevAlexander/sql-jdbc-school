package com.foxminded.school.domain.factory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.foxminded.school.domain.models.Course;

public class CourseGenerator implements ModelGenerator<List<Course>> {

    @Override
    public List<Course> generate(int counter) {
        Path courseData = Paths.get("C:\\Projects\\sql-jdbc-school\\src\\main\\resources\\course_data.txt");
        List<Course> coursesList = new ArrayList<>();
        
        try (Stream<String> fileStream = Files.lines(courseData)){
            coursesList = fileStream.limit(counter).map(line -> new Course(line)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coursesList;
    }
}
