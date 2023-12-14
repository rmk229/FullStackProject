package kz.yermek.testproject.services;

import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.models.Position;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    List<Employee> getEmployees();
    Employee getEmployee(int id);
    Employee save(Employee employee);
    void delete(int id);
    Employee update(int id, Employee employee);
    List<Employee> searchEmployeeByFirstName(String query);
    List<Employee> searchEmployeeBySecondName(String query);
    List<Employee> searchEmployeeByThirdName(String query);

}
