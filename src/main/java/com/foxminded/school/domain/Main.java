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

    public static void main(String[] args) {
        
        String url = "jdbc:postgresql://localhost:5432/school";
        String user = "postgres";
        String password = "1234";
        String create = "src\\main\\resources\\create_tables_script.sql";
        String drop = "src\\main\\resources\\drop_tables.sql";
        
        DBConfigDto config = new DBConfigDto(url, user, password);
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
 
        runner.executeScript(drop);
        runner.executeScript(create);
        
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
