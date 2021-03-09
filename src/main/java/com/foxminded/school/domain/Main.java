package com.foxminded.school.domain;

import java.util.List;
import java.util.Scanner;
import com.foxminded.school.domain.generators.CourseGenerator;
import com.foxminded.school.domain.generators.GroupGenerator;
import com.foxminded.school.domain.generators.StudentGenerator;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Group;
import com.foxminded.school.domain.models.Student;
import com.foxminded.school.domain.services.CourseService;
import com.foxminded.school.domain.services.GroupService;
import com.foxminded.school.domain.services.StudentService;
import com.foxminded.school.dao.*;

public class Main {
    
    private static final String CREATE = "src\\main\\resources\\create_tables_script.sql";
    private static final String DROP = "src\\main\\resources\\drop_tables.sql";
    private static final String CONFIG_FILE_PATH = "src\\main\\resources\\db_config.txt";
    
    public static void main(String[] args) {
        
        DBConfig config = new DBConfig(CONFIG_FILE_PATH);
        Runner runner = new Runner(config);
        GroupGenerator groupGenerator = new GroupGenerator();
        CourseGenerator courseGenerator = new CourseGenerator();
        StudentGenerator studentGenerator = new StudentGenerator();
        GroupService groupService = new GroupService(config);
        StudentService studentService = new StudentService(config);
        CourseService courseService = new CourseService(config);
        SortingHat sortingHat = new SortingHat();
        OptionMenu menu = new OptionMenu(studentService, courseService, groupService);
        Scanner scanner = new Scanner(System.in);
 
        runner.executeScript(DROP);
        runner.executeScript(CREATE);
        
        groupGenerator.generate(10).forEach(group -> groupService.add(group));
        studentGenerator.generate(200).forEach(student -> studentService.add(student));
        courseGenerator.generate(10).forEach(course -> courseService.add(course));
        
        List<Group> groupList = groupService.getAll(); 
        List<Student> studentList = studentService.getAll(); 
        List<Course> courseList = courseService.getAll();
        
        sortingHat.sortToGroup(studentList, groupList);
        sortingHat.assignToCourses(studentList, courseList);
        studentList.forEach(student -> studentService.update(student));
        
        System.out.println(menu.showMainMenu());
        System.out.println(menu.menuEngine(scanner.nextInt()));
        scanner.close();
    }
}
