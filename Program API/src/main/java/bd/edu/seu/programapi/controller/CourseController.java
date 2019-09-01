package bd.edu.seu.programapi.controller;

import bd.edu.seu.programapi.exception.ResourceAlreadyExistsException;
import bd.edu.seu.programapi.exception.ResourceDoesNotExistException;
import bd.edu.seu.programapi.model.Course;
import bd.edu.seu.programapi.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/courses")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Course> insertCourse(@RequestBody Course course) {
        try {
            Course insertedCourse = courseService.insertCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedCourse);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Course> getCourse(@PathVariable String code) {
        try {
            Course course = courseService.findById(code);
            return ResponseEntity.ok(course);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Course>> getCourse() {
        return ResponseEntity.ok().body(courseService.findAll());
    }

    @PutMapping(value = "")
    public ResponseEntity<Course> updateCourse(@RequestBody String code, @RequestBody Course course) {
        try {
            Course updateCourse = courseService.updateCourse(code, course);
            return ResponseEntity.ok().body(course);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<String> deleteCourse(@PathVariable String code) {
        try {
            boolean deleted = courseService.deleteById(code);
            return ResponseEntity.ok(code);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
