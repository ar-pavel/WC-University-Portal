package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @Test
    public void insertStudent() {
        Student student = new Student("129", "Reckless", "noName@yahoo.com", LocalDate.of(1991, 10, 12), 44, LocalDate.of(2016,07,10),"BScInCSE", 3.5, 100, "null");
        Student insertStudent = studentService.insertStudent(student);
        System.out.printf("Inserted Student: ", insertStudent);
    }

    @Test
    public void getStudent() {
        Student student = studentService.getStudent("125");
        System.out.println(student);
    }

    @Test
    public void getStudents() {
        List<Student> students = studentService.getStudents();
        students.forEach(System.out::println);
    }
}