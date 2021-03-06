package com.foxminded.school.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Group;
import com.foxminded.school.domain.models.Student;

public class SortingHat {
    
    Random random = new Random();
    
    public void sortToGroup(List<Student> students, List<Group> groups) {
        groups.forEach(group -> {
            int size = random.nextInt(30 - 10 + 1);
            size += 10;
            group.setGroupSize(size);});
        
        int indexCount = 0;
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            for (int j = 0; j < group.getGroupSize(); j++) {
                if (indexCount > students.size() - 1) {
                    break;
                }
                students.get(indexCount).setGroupId(group.getGroupID());
                indexCount++;
            }
        }
    }
    
    public void assignToCourses(List<Student> students, List<Course> courses) {
        for (Student student : students) {
            Set<Integer> courseSet = new HashSet<>();
            random.ints(1, courses.size() + 1)
                    .distinct()
                    .limit(random.nextInt(3) + 1)
                    .forEach(id -> courseSet.add(id));
            student.setCourses(courseSet);
        }
    }
}
