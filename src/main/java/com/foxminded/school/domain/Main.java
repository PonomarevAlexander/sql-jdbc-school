package com.foxminded.school.domain;

import java.util.List;
import java.util.Scanner;
import com.foxminded.school.domain.factory.CourseFactory;
import com.foxminded.school.domain.factory.GroupFactory;
import com.foxminded.school.domain.factory.StudentFactory;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Group;
import com.foxminded.school.domain.models.Student;
import com.foxminded.school.domain.services.CourseService;
import com.foxminded.school.domain.services.GroupService;
import com.foxminded.school.domain.services.StudentService;
import com.foxminded.school.dao.*;

public class Main {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/school";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private static final String CREATE = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP = "src\\main\\resources\\drop_tables.sql";
    
    public static void main(String[] args) {
        
        DBConfig config = new DBConfig(URL, USER, PASSWORD);
        Runner runner = new Runner(config);
        GroupFactory groupFactory = new GroupFactory();
        CourseFactory courseFactory = new CourseFactory();
        StudentFactory studentFactory = new StudentFactory();
        GroupService groupService = new GroupService(config);
        StudentService studentService = new StudentService(config);
        CourseService courseService = new CourseService(config);
        SortingHat sortingHat = new SortingHat();
        OptionMenu menu = new OptionMenu(studentService, courseService, groupService);
        Scanner scanner = new Scanner(System.in);
 
        runner.executeScript(DROP);
        runner.executeScript(CREATE);
        
        groupFactory.generate(10).forEach(group -> groupService.add(group));
        studentFactory.generate(200).forEach(student -> studentService.add(student));
        courseFactory.generate(10).forEach(course -> courseService.add(course));
        
        List<Group> groupList = groupService.getAll(); 
        List<Student> studentList = studentService.getAll(); 
        List<Course> courseList = courseService.getAll();
        
        sortingHat.sortToGroup(studentList, groupList);
        sortingHat.assignToCourses(studentList, courseList);
        studentList.forEach(student -> studentService.edit(student));
        
        System.out.println(menu.showMainMenu());
        System.out.println(menu.menuEngine(scanner.nextInt()));
        scanner.close();
    }
}
