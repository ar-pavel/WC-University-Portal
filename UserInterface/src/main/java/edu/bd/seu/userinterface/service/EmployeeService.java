package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeService {
    @Value("${employeeUrl}/employees")
    private String employeeUrl;
    private RestTemplate restTemplate;

    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Employee insertEmployee(Employee employee){
        return restTemplate.postForObject(employeeUrl, employee, Employee.class);
    }
    public Employee getEmployee(String id){
        return restTemplate.getForObject(employeeUrl + "/" + id, Employee.class);
    }

    public List<Employee> getEmployees( ){
        ResponseEntity<List<Employee>> response = restTemplate.exchange(
                employeeUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>(){});
        List<Employee> employees = response.getBody();
        return employees;
    }
    public Employee updateEmployee( Employee employee){
        HttpEntity<Employee> reqUpdate = new HttpEntity<>(employee);
        restTemplate.exchange(employeeUrl + "/" + employee.getId(), HttpMethod.PUT, reqUpdate, Employee.class);
        return reqUpdate.getBody();
    }
}
