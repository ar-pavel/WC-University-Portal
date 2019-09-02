package bd.edu.seu.studentapi.service;

import bd.edu.seu.studentapi.model.Student;
import org.junit.Before;
import org.junit.BeforeClass;
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

    @Before
    public void setUp() throws Exception {
        studentService.deleteALL();
    }


    @Test
    public void insertStudent() throws bd.edu.seu.studentapi.exception.ResourceAlreadyExistsException {
        Student student = new Student("125", "AR", "atikur1998@yahoo.com",LocalDate.of(1991, 10, 12), 44, LocalDate.of(2016,07,10),"BScInCSE", 3.5, 100, "null");
        Student insertStudent = studentService.insertStudent(student);
        assertEquals(student, insertStudent);
    }

    /*
    private String id;
    private String name;
    private String email;
    private LocalDate dob;
    private long batch;
    private LocalDate dateOfAdmission;
    private String Program;
    private Double cgpa;
    private long completedCredit;
    private String transactionId;
    */

    @Test
    public void findAll() {
        studentService.deleteALL();
        List<Student> all = studentService.findAll();
        assertEquals(all.size(), 0);
    }

    @Test
    public void findById() {
    }

    @Test
    public void updateStudent() {
        Student student = new Student("125", "AR", "atikur1998@yahoo.com",LocalDate.of(1991, 10, 12), 44, LocalDate.of(2016,07,10),"BScInCSE", 3.5, 100, "null");
        try {
            Student insertStudent = studentService.insertStudent(student);
            assertEquals(student, insertStudent);
            student.setId("125");
            Student updateStudent = studentService.updateStudent("126", insertStudent);
            assertEquals(updateStudent, insertStudent);
        } catch (bd.edu.seu.studentapi.exception.ResourceAlreadyExistsException | bd.edu.seu.studentapi.exception.ResourceDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteById() throws bd.edu.seu.studentapi.exception.ResourceDoesNotExistException, bd.edu.seu.studentapi.exception.ResourceAlreadyExistsException {
        Student student = new Student("125", "AR", "atikur1998@yahoo.com",LocalDate.of(1991, 10, 12), 44, LocalDate.of(2016,07,10),"BScInCSE", 3.5, 100, "null");
        Student insertStudent = studentService.insertStudent(student);
        boolean delete = studentService.deleteById(student.getId());
        assertTrue(delete);

    }

    @Test
    public void deleteALL() {
        studentService.deleteALL();
        List<Student> all = studentService.findAll();
        assertEquals(all.size(), 0);
    }
}