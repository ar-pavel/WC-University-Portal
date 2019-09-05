package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Student;
import edu.bd.seu.userinterface.model.StudentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StudentService {
    @Value("${studentUrl}/students")
    private String studentUrl;
    private RestTemplate restTemplate;

    public StudentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Student insertStudent(Student student){
        HttpEntity<Student> request = new HttpEntity<>(student);
        ResponseEntity<Student> response = restTemplate
                .exchange(studentUrl, HttpMethod.POST, request, Student.class);
//        Student response = restTemplate.postForObject(studentUrl, student, Student.class);
        return response.getBody();
    }
    public Student getStudent(String id){
        Student student = restTemplate.getForObject(studentUrl + "/" + id, Student.class);
        return student;
    }
    public List<Student> getStudents( ){
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                studentUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>(){});
        List<Student> students = response.getBody();
        return students;
    }




}
