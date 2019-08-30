package bd.edu.seu.studentapi.service;

import bd.edu.seu.studentapi.exception.ResourceAlreadyExistsException;
import bd.edu.seu.studentapi.exception.ResourceDoesNotExistException;
import bd.edu.seu.studentapi.model.Student;
import bd.edu.seu.studentapi.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student insertStudent(Student student) throws ResourceAlreadyExistsException {
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        if (optionalStudent.isPresent()) {
            throw new ResourceAlreadyExistsException(student.getId());
        } else {
            return studentRepository.save(student);
        }
    }

    public List<Student> findAll() {
        List<Student> studentList = new ArrayList<>();
        studentList.addAll((Collection<? extends Student>) studentRepository.findAll());
        return studentList;
    }

    public Student findById(String id) throws ResourceDoesNotExistException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        } else throw new ResourceDoesNotExistException(id);
    }

    public Student updateStudent(String id, Student student) throws ResourceDoesNotExistException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            student.setId(id);
            return studentRepository.save(student);
        } else {
            throw new ResourceDoesNotExistException(id);
        }
    }

    public boolean deleteById(String id) throws ResourceDoesNotExistException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        optionalStudent.ifPresent(student -> studentRepository.deleteById(id));
        optionalStudent.orElseThrow(() -> new ResourceDoesNotExistException(id));
        return true;
    }

    public void deleteALL() {
        studentRepository.deleteAll();
    }
}
