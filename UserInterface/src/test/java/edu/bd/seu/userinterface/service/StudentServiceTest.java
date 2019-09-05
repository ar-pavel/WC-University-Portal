package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Course;
import edu.bd.seu.userinterface.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @Test
    public void insertStudent() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CSE1011","Programming Language I (C)",3.0));
        courses.add(new Course("CSE1012","Programming Language I (C) lab",1.0));
        courses.add(new Course("CSE1013","Computer Fundamentals",3.0));
        courses.add(new Course("CSE1021","Discrete Mathematics",3.0));
        courses.add(new Course("CSE1033","Data Structure",3.0));
        courses.add(new Course("CSE1034","Data Structure Lab",1.0));
        courses.add(new Course("CSE2013","Digital Logic Design",3.0));
        courses.add(new Course("CSE2014","Digital Logic Design Lab",1.0));

        Student student =  new Student("2015200000021", "Morshed Alam", "2015200000021.seu.edu.bd", LocalDate.of(1996, 10, 12), 43, LocalDate.of(2016,04,10),"BScInCSE", 3.29, 122, courses);

        Student insertStudent = studentService.insertStudent(student);

        System.out.println(insertStudent);
    }

    @Test
    public void getStudent() {
        Student student = studentService.getStudent("2016100000003");
        System.out.println(student);
    }

    @Test
    public void getStudents() {
        List<Student> students = studentService.getStudents();
        students.forEach(System.out::println);
    }

    @Test
    public void testInsertStudent() {
    }

    @Test
    public void testGetStudent() {
    }

    @Test
    public void testGetStudents() {
    }

//    @Test
//    public void updateStudent() {
//        List<Course> courses = new ArrayList<>();
//        courses.add(new Course("CSE1011","Programming Language I (C)",3.0));
//        courses.add(new Course("CSE1012","Programming Language I (C) lab",1.0));
//        courses.add(new Course("CSE1013","Computer Fundamentals",3.0));
//        courses.add(new Course("CSE1021","Discrete Mathematics",3.0));
//        courses.add(new Course("CSE1033","Data Structure",3.0));
//        courses.add(new Course("CSE1034","Data Structure Lab",1.0));
//        courses.add(new Course("CSE2013","Digital Logic Design",3.0));
//        courses.add(new Course("CSE2014","Digital Logic Design Lab",1.0));
//
//        Student student =  new Student("2015200000021", "Morshed Alam", "2015200000021.seu.edu.bd", LocalDate.of(1996, 10, 12), 41, LocalDate.of(2016,04,10),"BScInCSE", 3.29, 122, courses);
//        Student updateStudent = studentService.updateStudent(student.getId(), student);
//
//        System.out.println(updateStudent);
//
//    }
}