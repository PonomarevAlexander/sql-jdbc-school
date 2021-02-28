package com.foxminded.school.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.foxminded.school.dao.ConnectionHandler;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Group;
import com.foxminded.school.domain.models.Student;
import com.foxminded.school.domain.services.CourseService;
import com.foxminded.school.domain.services.StudentService;

public class SortingHat {
    
    private static final String URL = "jdbc:postgesql://localhost:5432/school";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    
    Random random = new Random();
    StudentService studentService = new StudentService(new ConnectionHandler(URL, USER, PASSWORD));
    CourseService courseService = new CourseService(new ConnectionHandler(URL, USER, PASSWORD));
    
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
