CREATE table groups(
    group_id serial primary key,
    group_name varchar(255) not  null);
    
CREATE TABLE students(
    student_id serial primary key,
    group_id integer,
    first_name varchar(255) not null,
    last_name varchar(255) not null);
    
CREATE TABLE courses(
    course_id serial primary key,
    course_name varchar(255) not null,
    course_description text);
    
CREATE TABLE students_courses(
    student_id integer references students(student_id) on delete cascade,
    course_id integer references courses(course_id) on delete cascade);