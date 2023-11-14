package kz.yermek.testproject.services.impl;

import kz.yermek.testproject.models.Department;
import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.repositories.EmployeeRepository;
import kz.yermek.testproject.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(int id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public Employee update(int id, Employee employee) {
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> searchEmployeeByFirstName(String query) {
        return employeeRepository.findByFirstNameEqualsIgnoreCase(query);
    }

    @Override
    public List<Employee> searchEmployeeBySecondName(String query) {
        return employeeRepository.findBySecondNameEqualsIgnoreCase(query);

    }

    @Override
    public List<Employee> searchEmployeeByThirdName(String query) {
        return employeeRepository.findByThirdNameEqualsIgnoreCase(query);

    }
}
