package bd.edu.seu.programapi.service;

import bd.edu.seu.programapi.exception.ResourceAlreadyExistsException;
import bd.edu.seu.programapi.exception.ResourceDoesNotExistException;
import bd.edu.seu.programapi.model.Course;
import bd.edu.seu.programapi.model.ProgramName;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;

//    public CourseServiceTest(CourseService courseService) {
//        this.courseService = courseService;
//    }


    @Before
    public void setUp() throws Exception {
        courseService.deleteALL();
    }

    @Test
    public void insertCourse() throws ResourceAlreadyExistsException {
        Course course = new Course("CSE1011","Computer Fundamental",3.0);
        Course insertCourse = courseService.insertCourse(course);
        assertEquals(course,insertCourse);
    }

    @Test
    public void findAll() {
        List<Course> courseList = courseService.findAll();
        List<Course> courseList1 = courseService.findAll();
        assertEquals(courseList.size(),courseList1.size());
    }

    @Test
    public void findById() throws ResourceAlreadyExistsException, ResourceDoesNotExistException {
        Course course = new Course("CSE1011","Computer Fundamental", 3.0);
        Course insertCourse = courseService.insertCourse(course);
        Course byId = courseService.findById(course.getCode());
        assertEquals(course,byId);
    }

    @Test
    public void updateCourse() throws ResourceDoesNotExistException, ResourceAlreadyExistsException {
        Course course = new Course("CSE1011","Computer Fundamental", 3.0);
        courseService.insertCourse(course);
        Course updatedcourse = new Course("CSE1011","Computer Fundamental Lab", 3.0);
        Course updateCourse = courseService.updateCourse(course.getCode(), updatedcourse);
        assertEquals(updateCourse,updatedcourse);

    }

    @Test
    public void deleteById() {
        Course course = new Course("CSE1011","Computer Fundamental", 3.0);
        boolean state = false;
        try {
            Course insertCourse = courseService.insertCourse(course);
            state = courseService.deleteById(course.getCode());
            assertTrue(state);
        } catch (ResourceAlreadyExistsException | ResourceDoesNotExistException e) {
//            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void deleteALL() {
        courseService.deleteALL();
        List<Course> all = courseService.findAll();
        assertEquals(all.size(), 0);
    }
}