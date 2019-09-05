package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Course;
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
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;
    @Test
    public void insertCourse() {
        Course course = new Course();
        Course insertCourse = courseService.insertCourse(course);
        System.out.println(insertCourse);
    }

    @Test
    public void getCourse() {
        Course course = courseService.getCourse("");
        System.out.println(course);
    }

    @Test
    public void getCourses() {
        List<Course> courses = courseService.getCourses();
        courses.forEach(System.out::println);
    }
}