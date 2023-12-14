package kz.yermek.testproject.controllers;


import kz.yermek.testproject.models.Department;
import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.services.DepartmentService;
import kz.yermek.testproject.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) throws URISyntaxException {
        Department savedDepartment = departmentService.save(department);
        return ResponseEntity.created(new URI("/departments/" +
                savedDepartment.getId())).body(savedDepartment);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Department> details(@PathVariable int id) {
        Department department = departmentService.getDepartment(id);

        if (department != null) {
            return new ResponseEntity<>(department, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/details/{id}")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department newDepartment, @PathVariable int id) {
        Department department = departmentService.getDepartment(id);
        if (department != null && newDepartment != null) {
            department.setDepartmentName(newDepartment.getDepartmentName());
            departmentService.update(department);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable int id) {
        Department department = departmentService.getDepartment(id);
        List<Employee> employees = employeeService.getEmployees();

        for (Employee employee: employees) {
            if (employee.getDepartment().getId() == id) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        if (department != null) {
            departmentService.delete(department);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
