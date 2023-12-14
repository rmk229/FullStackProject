package kz.yermek.testproject.services;

import kz.yermek.testproject.models.Department;
import kz.yermek.testproject.models.Employee;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> getDepartments();
    Department addDepartment(Department department);
    Department update(Department department);
    Department getDepartment(int id);
    void delete(Department department);
    Department save(Department department);
    List<Department> searchDepartment(String query);
}
