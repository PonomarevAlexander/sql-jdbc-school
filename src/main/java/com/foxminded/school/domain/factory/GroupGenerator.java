package com.foxminded.school.domain.factory;

import java.util.ArrayList;
import java.util.List;
import com.foxminded.school.domain.models.Group;
import com.github.javafaker.Faker;

public class GroupFactory implements ModelFactory<List<Group>> {

    private static final String TWO_CHARS = "[a-z]{2}";
    private static final String TWO_DIGITS = "[0-99]{2}";
    
    @Override
    public List<Group> generate(int count) {
        Faker faker = new Faker();
        List<Group> result = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            String groupName = String.format("%s--%s", faker.regexify(TWO_CHARS), faker.regexify(TWO_DIGITS));
            result.add(new Group(groupName));
        }
        return result;
    }
}
