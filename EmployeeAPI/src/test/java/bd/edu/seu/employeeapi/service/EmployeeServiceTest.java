package bd.edu.seu.employeeapi.service;

import bd.edu.seu.employeeapi.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;


//
//    @Test
//    public void findAll() {
//        List<Employee> all = employeeService.findAll();
//        System.out.println(all);
//    }
//
//
    @Test
    public void deleteALl() {
        employeeService.deleteALL();
        System.out.println(employeeService.findAll());

    }

    @Test
    public void name() {
    }
}