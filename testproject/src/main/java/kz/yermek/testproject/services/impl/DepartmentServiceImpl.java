package kz.yermek.testproject.services.impl;

import kz.yermek.testproject.models.Department;
import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.repositories.DepartmentRepository;
import kz.yermek.testproject.repositories.EmployeeRepository;
import kz.yermek.testproject.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department update(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartment(int id) {
        return departmentRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> searchDepartment(String query) {
        return departmentRepository.findByDepartmentNameEqualsIgnoreCase(query);
    }
}
