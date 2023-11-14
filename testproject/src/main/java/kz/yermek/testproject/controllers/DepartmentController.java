package kz.yermek.testproject.controllers;


import kz.yermek.testproject.models.Department;
import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.services.DepartmentService;
import kz.yermek.testproject.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping
    public String getAllPositions(Model model) {
        List<Department> departments = departmentService.getDepartments();
        model.addAttribute("departments", departments);

        return "departments";
    }

    @GetMapping("/add")
    public String getForm() {
        return "departmentsAdd";
    }

    @PostMapping("/add")
    public String addPosition(@RequestParam(name = "department_name") String departmentName) {
        if (departmentName != null) {
            Department department = new Department();
            department.setDepartmentName(departmentName);
            departmentService.addDepartment(department);
        }

        return "redirect:/departments";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable(name = "id") int id) {
        Department department = departmentService.getDepartment(id);
        model.addAttribute("department", department);
        return "departmentsDetails";
    }

    @PostMapping("/details")
    public String updateDepartment(@RequestParam(name = "id", defaultValue = "0") int id,
                                   @RequestParam(name = "department") String departmentName) {
        Department department = departmentService.getDepartment(id);
        if (department != null && departmentName != null) {
            department.setDepartmentName(departmentName);
            departmentService.update(department);
        }

        return "redirect:/departments";
    }

    @PostMapping(value = "/deleteDepartment")
    public String deleteEmployee(@RequestParam(name = "id", defaultValue = "0") int id) {
        Department department = departmentService.getDepartment(id);

        //How to escape a white page with error
        List<Employee> employees = employeeService.getEmployees();
        for (Employee employee: employees) {
            if (employee.getDepartment().getId() == id) {
                return "redirect:/departments";
            }
        }
        if (department != null) {
            departmentService.delete(department);
        }

        return "redirect:/departments";
    }
}
