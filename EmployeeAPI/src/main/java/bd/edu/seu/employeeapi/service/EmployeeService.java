package bd.edu.seu.employeeapi.service;

import bd.edu.seu.employeeapi.exceptions.ResourceAlreadyExistsException;
import bd.edu.seu.employeeapi.exceptions.ResourceDoesNotExistException;
import bd.edu.seu.employeeapi.model.Employee;
import bd.edu.seu.employeeapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee insertEmployee(Employee employee) throws ResourceAlreadyExistsException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
        if (optionalEmployee.isPresent()) {
            throw new ResourceAlreadyExistsException(employee.getId());
        } else {
            return employeeRepository.save(employee);
        }
    }

    public List<Employee> findAll() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.addAll((Collection<? extends Employee>) employeeRepository.findAll());
        return employeeList;
    }

    public Employee findById(String id) throws ResourceDoesNotExistException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        } else throw new ResourceDoesNotExistException(id);
    }

    public Employee updateEmployee(String id, Employee employee) throws ResourceDoesNotExistException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            employee.setId(id);
            return employeeRepository.save(employee);
        } else {
            throw new ResourceDoesNotExistException(id);
        }
    }

    public boolean deleteById(String id) throws ResourceDoesNotExistException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.ifPresent(employee -> employeeRepository.deleteById(id));
        optionalEmployee.orElseThrow(() -> new ResourceDoesNotExistException(id));
        return true;
    }

    public void deleteALL() {
        employeeRepository.deleteAll();
    }
}