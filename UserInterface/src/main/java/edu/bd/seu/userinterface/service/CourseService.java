package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Course;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CourseService {
    @Value("${courseUrl}/courses")
    private String courseUrl;
    private RestTemplate restTemplate;

    public CourseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Course insertCourse(Course course){
        return restTemplate.postForObject(courseUrl, course, Course.class);
    }
    public Course getCourse(String id){
        return restTemplate.getForObject(courseUrl + "/" + id, Course.class);
    }

    public List<Course> getCourses( ){
        ResponseEntity<List<Course>> response = restTemplate.exchange(
                courseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Course>>(){});
        List<Course> courses = response.getBody();
        return courses;
    }
    public Course updateCourse( Course course){
        HttpEntity<Course> reqUpdate = new HttpEntity<>(course);
        restTemplate.exchange(courseUrl + "/" + course.getCode(), HttpMethod.PUT, reqUpdate, Course.class);
        return reqUpdate.getBody();
    }

}
