package bd.edu.seu.studentapi.controller;

import bd.edu.seu.studentapi.exception.ResourceAlreadyExistsException;
import bd.edu.seu.studentapi.exception.ResourceDoesNotExistException;
import bd.edu.seu.studentapi.model.Student;
import bd.edu.seu.studentapi.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Student> insertStudent(@RequestBody Student student) {
        try {
            Student insertedStudent = studentService.insertStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedStudent);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable String id) {
        try {
            Student student = studentService.findById(id);
            return ResponseEntity.ok(student);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Student>> getStudent() {
        return ResponseEntity.ok().body(studentService.findAll());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student student) {
        try {
            Student updateStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok().body(student);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        try {
            boolean deleted = studentService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status")
    public String status() {
        return "Working!";
    }

}
