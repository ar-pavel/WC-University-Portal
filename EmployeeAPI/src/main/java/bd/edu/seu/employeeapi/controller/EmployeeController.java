package bd.edu.seu.employeeapi.controller;

import bd.edu.seu.employeeapi.exceptions.ResourceAlreadyExistsException;
import bd.edu.seu.employeeapi.exceptions.ResourceDoesNotExistException;
import bd.edu.seu.employeeapi.model.Employee;
import bd.edu.seu.employeeapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Employee> insertEmployee(@RequestBody Employee employee) {
        try {
            Employee insertedEmployee = employeeService.insertEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedEmployee);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String code) {
        try {
            Employee employee = employeeService.findById(code);
            return ResponseEntity.ok(employee);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Employee>> getEmployee() {
        return ResponseEntity.ok().body(employeeService.findAll());
    }

    @PutMapping(value = "")
    public ResponseEntity<Employee> updateEmployee(@RequestBody String code, @RequestBody Employee employee) {
        try {
            Employee updateEmployee = employeeService.updateEmployee(code, employee);
            return ResponseEntity.ok().body(employee);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String code) {
        try {
            boolean deleted = employeeService.deleteById(code);
            return ResponseEntity.ok(code);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

}


