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
        Runner runner = new Runner();
        GroupFactory groupFactory = new GroupFactory();
        GroupService groupService = new GroupService();
        StudentFactory studentFactory = new StudentFactory();
        StudentService studentService = new StudentService();
        CourseFactory courseFactory = new CourseFactory();
        CourseService courseService = new CourseService();
        SortingHat sortingHat = new SortingHat();
        OptionMenu menu = new OptionMenu();
        Scanner scanner = new Scanner(System.in);

        
        runner.executeScript("src\\main\\resources\\create_tables_script.sql");
        
        groupFactory.generate(10).forEach(group -> groupService.add(group));
        studentFactory.generate(200).forEach(student -> studentService.add(student));
        courseFactory.generate(10).forEach(course -> courseService.add(course));
        
        List<Group> groupList = groupService.getAll(); 
        List<Student> studentList =studentService.getAll(); 
        List<Course> courseList = courseService.getAll();
        
        sortingHat.sortToGroup(studentList, groupList);
        sortingHat.assignToCourses(studentList, courseList);
        studentList.forEach(student -> studentService.edit(student));
        
        menu.showMainMenu();
        menu.menuItems(scanner.nextInt());
        scanner.close();
        
        runner.executeScript("src\\main\\resources\\drop_tables.sql");
    }
}
