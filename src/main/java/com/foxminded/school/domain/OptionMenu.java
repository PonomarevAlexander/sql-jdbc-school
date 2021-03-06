package com.foxminded.school.domain;

import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;
import com.foxminded.school.domain.models.Course;
import com.foxminded.school.domain.models.Student;
import com.foxminded.school.domain.services.CourseService;
import com.foxminded.school.domain.services.GroupService;
import com.foxminded.school.domain.services.StudentService;

public class OptionMenu {

    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";
    private static final String TRY_AGAIN = "Try it again";
    private static final String SUCCESS = "Success!";
    private static final String SELECT_COURSE = "Select course...";
    private static final String ENTER_FULL_NAME = "Enter student full name...";
    private static final String ENTER_ID = "Enter ID...";
    private static final String ENTER_COURSE_NAME = "Enter course name...";
    private static final String ALREADY = "this course has already been added";
    private static final String DASH = "------------------------------------------------------------";
    private static final String EMPTY = "";
    private static final String FIND_ALL_GROUPS_EQUALS = "Find all groups with less or equals student count";
    private static final String FIND_ALL_STUDENTS_COURSE = "Find all students related to course with given name";
    private static final String ADD_NEW_STUDENT = "Add new student";
    private static final String DELETE_STUDENT = "Delete student by ID";
    private static final String ADD_STUDENT_TO_COURSE = "Add a student to the course (from a list)";
    private static final String REMOVE_STUDENT_FROM_COURSE = "Remove the student from one of his or her course";
   
    private StudentService studentService;
    private CourseService courseService;
    private GroupService groupService;
    private Scanner scanner = new Scanner(System.in);
    private ConsoleFormatter formatter = new ConsoleFormatter();
    
    public OptionMenu(StudentService studentService, CourseService courseService, GroupService groupService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.groupService = groupService;
    }

    public String showMainMenu() {
        StringJoiner joiner = new StringJoiner(EMPTY);
        joiner.add(DASH + NEW_LINE +
                1 + SPACE + FIND_ALL_GROUPS_EQUALS + NEW_LINE +
                2 + SPACE + FIND_ALL_STUDENTS_COURSE + NEW_LINE +
                3 + SPACE + ADD_NEW_STUDENT + NEW_LINE +
                4 + SPACE + DELETE_STUDENT + NEW_LINE +
                5 + SPACE + ADD_STUDENT_TO_COURSE + NEW_LINE +
                6 + SPACE + REMOVE_STUDENT_FROM_COURSE + NEW_LINE +
                DASH);
        return joiner.toString();
    }
    
    public String menuEngine(int select) {
        String result;
        
        if (select == 1) {
            result = getMinStudents();
        } else if (select == 2) {
            print(ENTER_COURSE_NAME);
            result = getStudentsRelatedCourse();
        } else if (select == 3) {
            print(ENTER_FULL_NAME);
            addNewStudent();
            result = SUCCESS;
        } else if (select == 4) {
            print(ENTER_ID);
            deleteStudent();
            result = SUCCESS;
        } else if (select == 5) {
            print(ENTER_ID);
            if (addStudentToCourse()) {
                result = SUCCESS;
            } else {
                result = ALREADY;
            }
        } else if (select == 6) {
            print(ENTER_ID);
            removeStudentFromCourse();
            result = SUCCESS;
        } else {
            result = TRY_AGAIN;
        }
        scanner.close();
        return result;
    }
    
    private void print(String string) {
        System.out.println(string);
    }
    
    private String getMinStudents() {
        return groupService.getMinStudentCountIntoGroups();
    }
    
    private String getStudentsRelatedCourse() {
        String courseName = EMPTY;
        if (scanner.hasNextLine()) {
            courseName = scanner.nextLine();
        }
        Course course = courseService.getbyName(courseName);
        return studentService.getStudentsFromGivenCourse(course);
    }
    
    private void addNewStudent() {
        String name = EMPTY;
        String lastName = EMPTY;
        if (scanner.hasNextLine()) {
            name = scanner.nextLine();
        }
        if (scanner.hasNextLine()) {
            lastName = scanner.nextLine();
        }
        studentService.add(new Student(name, lastName));
    }
    
    private void deleteStudent() {
        int id = 0;
        if (scanner.hasNextInt()) {
            id = scanner.nextInt();
        }
        studentService.remove(id);
    }
    
    private boolean addStudentToCourse() {
        int id = 0;
        if (scanner.hasNextInt()) {
            id = scanner.nextInt();
        }
        Student student = studentService.getById(id);
        Set<Integer> courses = studentService.getStudentCourses(student);
        student.setCourses(courses);
        print(SELECT_COURSE);
        print(formatter.formatCoursesList(courseService.getAll()));
        if(student.getCourses().add(scanner.nextInt())) {
            studentService.addCourses(student);
            return true;
        } else {
            return false;
        }
    }
    
    private void removeStudentFromCourse() {
        Student student = studentService.getById(scanner.nextInt());
        print(SELECT_COURSE);
        print(formatter.formatCoursesList(courseService.getAll()));
        studentService.removeFromCourse(student, scanner.nextInt());
    }
}
