package com.foxminded.school.domain.generators;

import java.util.ArrayList;
import java.util.List;
import com.foxminded.school.domain.models.Student;
import com.github.javafaker.Faker;

public class StudentGenerator implements ModelGenerator<List<Student>>{
    
    @Override
    public List<Student> generate(int count) {
        List<Student> result = new ArrayList<>();
        Faker faker = new Faker();
        
        for(int i = 0; i < count; i++) {
            result.add(new Student(faker.name().firstName(), faker.name().lastName()));
        }
        return result;
    }
}
