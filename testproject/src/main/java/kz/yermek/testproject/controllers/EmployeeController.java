package kz.yermek.testproject.controllers;

import jakarta.servlet.http.HttpServletResponse;
import kz.yermek.testproject.models.*;
import kz.yermek.testproject.services.*;

import kz.yermek.testproject.util.ExcelExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final PositionService positionService;
    private final RankService rankService;
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
        Employee employee = employeeService.getEmployee(id);

        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public ResponseEntity<Employee> createEmployee(@ModelAttribute EmployeeRequest employee) {

        Position position = positionService.getPosition(employee.getPosition());
        Rank rank = rankService.getRank(employee.getRank());
        Department department = departmentService.getDepartment(employee.getDepartment());
        Images images = imageService.getImage(employee.getImages());

        if (position != null && rank != null && department != null && images != null) {

            Employee newEmployee = new Employee();

            newEmployee.setPersonalNumber(employee.getPersonalNumber());
            newEmployee.setSecondName(employee.getSecondName());
            newEmployee.setFirstName(employee.getFirstName());
            newEmployee.setThirdName(employee.getThirdName());

            newEmployee.setDateOfBirth(employee.getDateOfBirth());
            newEmployee.setDateOfSign(employee.getDateOfSign());
            newEmployee.setContractPeriod(employee.getContractPeriod());

            newEmployee.setPosition(position);
            newEmployee.setRank(rank);
            newEmployee.setDepartment(department);
            newEmployee.setImages(images);

            employeeService.addEmployee(newEmployee);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/details/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeRequest employee, @PathVariable int id) {
        Employee newEmployee = employeeService.getEmployee(id);

        Position position = positionService.getPosition(employee.getPosition());
        Rank rank = rankService.getRank(employee.getRank());
        Department department = departmentService.getDepartment(employee.getDepartment());

        if (position != null && rank != null && department != null) {

            newEmployee.setPersonalNumber(employee.getPersonalNumber());
            newEmployee.setSecondName(employee.getSecondName());
            newEmployee.setFirstName(employee.getFirstName());
            newEmployee.setThirdName(employee.getThirdName());

            newEmployee.setDateOfBirth(employee.getDateOfBirth());
            newEmployee.setDateOfSign(employee.getDateOfSign());
            newEmployee.setContractPeriod(employee.getContractPeriod());

            newEmployee.setPosition(position);
            newEmployee.setRank(rank);
            newEmployee.setDepartment(department);

            employeeService.update(id, newEmployee);
        }
        return ResponseEntity.ok().body(newEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int id) {
        try {
            employeeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/excelExport")
    public void excelExport(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            //Написание названия эксель документа
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);

            List<Employee> employeeList = employeeService.getEmployees();
            ExcelExporter excelExporter = new ExcelExporter(employeeList);
            excelExporter.export(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}