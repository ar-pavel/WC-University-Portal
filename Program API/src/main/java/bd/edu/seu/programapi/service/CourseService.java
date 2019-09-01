package bd.edu.seu.programapi.service;

import bd.edu.seu.programapi.exception.ResourceAlreadyExistsException;
import bd.edu.seu.programapi.exception.ResourceDoesNotExistException;
import bd.edu.seu.programapi.model.Course;
import bd.edu.seu.programapi.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course insertCourse(Course course) throws ResourceAlreadyExistsException {
        Optional<Course> optionalCourse = courseRepository.findById(course.getCode());
        if (optionalCourse.isPresent()) {
            throw new ResourceAlreadyExistsException(course.getCode());
        } else {
            return courseRepository.save(course);
        }
    }

    public List<Course> findAll() {
        List<Course> courseList = new ArrayList<>();
        courseList.addAll((Collection<? extends Course>) courseRepository.findAll());
        return courseList;
    }

    public Course findById(String code) throws ResourceDoesNotExistException {
        Optional<Course> optionalCourse = courseRepository.findById(code);
        if (optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else throw new ResourceDoesNotExistException(code);
    }

    public Course updateCourse(String code, Course course) throws ResourceDoesNotExistException {
        Optional<Course> optionalCourse = courseRepository.findById(code);
        if (optionalCourse.isPresent()) {
            course.setCode(code);
            return courseRepository.save(course);
        } else {
            throw new ResourceDoesNotExistException(code);
        }
    }

    public boolean deleteById(String code) throws ResourceDoesNotExistException {
        Optional<Course> optionalCourse = courseRepository.findById(code);
        optionalCourse.ifPresent(course -> courseRepository.deleteById(code));
        optionalCourse.orElseThrow(() -> new ResourceDoesNotExistException(code));
        return true;
    }

    public void deleteALL() {
        courseRepository.deleteAll();
    }
}