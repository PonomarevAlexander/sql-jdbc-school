package com.foxminded.school.domain;

import java.util.Scanner;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Student;
import com.foxminded.school.domain.services.CourseService;
import com.foxminded.school.domain.services.GroupService;
import com.foxminded.school.domain.services.StudentService;

public class OptionMenu {
    
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();
    private GroupService groupService = new GroupService();
    private Scanner scanner = new Scanner(System.in);
    private ConsoleFormatter formatter = new ConsoleFormatter();
    
    private static final String NEW_LINE = "\n";
    private static final String TRY_AGAIN = "Try it again";
    private static final String SUCCESS = "Success!";
    private static final String SELECT_COURSE = "Select course...";
    private static final String ENTER_FULL_NAME = "Enter student full name...";
    private static final String ENTER_ID = "Enter ID...";
    private static final String ENTER_COURSE_NAME = "Enter course name...";
    private static final String ALREADY = "this course has already been added";
    private static final String DASH = "------------------------------------------------------------";
    private static final String FIND_ALL_GROUPS_EQUALS = "Find all groups with less or equals student count";
    private static final String FIND_ALL_STUDENTS_COURSE = "Find all students related to course with given name";
    private static final String ADD_NEW_STUDENT = "Add new student";
    private static final String DELETE_STUDENT = "Delete student by ID";
    private static final String ADD_STUDENT_TO_COURSE = "Add a student to the course (from a list)";
    private static final String REMOVE_STUDENT_FROM_COURSE = "Remove the student from one of his or her course";

    public void showMainMenu() {
        print(DASH);
        print(NEW_LINE);
        print(1 + " " + FIND_ALL_GROUPS_EQUALS);
        print(2 + " " + FIND_ALL_STUDENTS_COURSE);
        print(3 + " " + ADD_NEW_STUDENT);
        print(4 + " " + DELETE_STUDENT);
        print(5 + " " + ADD_STUDENT_TO_COURSE);
        print(6 + " " + REMOVE_STUDENT_FROM_COURSE);
        print(NEW_LINE);
        print(DASH);
        
    }
    
    public void menuItems(int select) {
        if (select == 1) {
            print(groupService.getMinStudentCountIntoGroups());
        } else if (select == 2) {
            print(ENTER_COURSE_NAME);
            Course course = courseService.getbyName(scanner.nextLine());
            print(studentService.getStudentsFromGivenCourse(course));
        } else if (select == 3) {
            print(ENTER_FULL_NAME);
            studentService.add(new Student(
                    scanner.nextLine(),
                    scanner.nextLine()));
            print(SUCCESS);
        } else if (select == 4) {
            print(ENTER_ID);
            studentService.remove(scanner.nextInt());
            print(SUCCESS);
        } else if (select == 5) {
            print(ENTER_ID);
            Student student = studentService.get(scanner.nextInt());
            student.setCourses(studentService.getStudentCourses(student));
            print(SELECT_COURSE);
            print(formatter.formatCoursesList(courseService.getAll()));
            if(student.getCourses().add(scanner.nextInt())) {
                studentService.addCourses(student);
                print(SUCCESS);
            } else {
                print(ALREADY);
            }
        } else if (select == 6) {
            print(ENTER_ID);
            Student student = studentService.get(scanner.nextInt());
            print(SELECT_COURSE);
            print(formatter.formatCoursesList(courseService.getAll()));
            studentService.removeFromCourse(student, scanner.nextInt());
            print(SUCCESS);
        } else {
            print(TRY_AGAIN);
        }
        scanner.close();
    }
    
    private void print(String string) {
        System.out.println(string);
    }
}
