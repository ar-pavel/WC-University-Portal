package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Student;
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
        return restTemplate.postForObject(studentUrl, student, Student.class);
    }
    public Student getStudent(String id){
        return restTemplate.getForObject(studentUrl + "/" + id, Student.class);
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
    public Student updateStudent( Student student){
        HttpEntity<Student> reqUpdate = new HttpEntity<>(student);
        restTemplate.exchange(studentUrl + "/" + student.getId(), HttpMethod.PUT, reqUpdate, Student.class);
        return reqUpdate.getBody();
    }


}
