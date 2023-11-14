package kz.yermek.testproject.controllers;

import jakarta.servlet.http.HttpServletResponse;
import kz.yermek.testproject.models.Department;
import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.models.Position;
import kz.yermek.testproject.models.Rank;
import kz.yermek.testproject.services.DepartmentService;
import kz.yermek.testproject.services.EmployeeService;
import kz.yermek.testproject.services.PositionService;
import kz.yermek.testproject.services.RankService;
import kz.yermek.testproject.util.ExcelExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final PositionService positionService;
    private final RankService rankService;
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";

    @GetMapping
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);

        List<Department> departments = departmentService.getDepartments();
        model.addAttribute("departments", departments);

        List<Position> positions = positionService.getPositions();
        model.addAttribute("positions", positions);

        List<Rank> ranks = rankService.getRanks();
        model.addAttribute("ranks", ranks);

        return "employees";
    }

    @GetMapping(value = "/about")
    public String aboutEmployees() {
        return "employeesAbout";
    }

    @GetMapping(value = "/add")
    public String getForm(Model model) {

        List<Department> departments = departmentService.getDepartments();
        model.addAttribute("departments", departments);

        List<Position> positions = positionService.getPositions();
        model.addAttribute("positions", positions);

        List<Rank> ranks = rankService.getRanks();
        model.addAttribute("ranks", ranks);

        return "employeesAdd";
    }

    @PostMapping(value = "/add")
    public String addEmployee(@RequestParam(name = "personal_number", defaultValue = "0") int personalNumber,
                              @RequestParam(name = "second_name", defaultValue = " ") String secondName,
                              @RequestParam(name = "first_name", defaultValue = " ") String firstName,
                              @RequestParam(name = "third_name", defaultValue = " ") String thirdName,
                              @RequestParam(name = "date_of_birth")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                              @RequestParam(name = "position", defaultValue = "0") int positionId,
                              @RequestParam(name = "rank", defaultValue = "0") int rankId,
                              @RequestParam(name = "department", defaultValue = "0") int departmentId,
                              @RequestParam(name = "date_of_sign")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfSign,
                              @RequestParam(name = "contract_period") int contractPeriod,
                              @RequestParam(name = "fileImage") MultipartFile file) {

        Position position = positionService.getPosition(positionId);
        Rank rank = rankService.getRank(rankId);
        Department department = departmentService.getDepartment(departmentId);

        if (position != null && rank != null && department != null) {
            Employee employee = new Employee();

            employee.setPersonalNumber(personalNumber);
            employee.setSecondName(secondName);
            employee.setFirstName(firstName);
            employee.setThirdName(thirdName);
            employee.setDateOfBirth(dateOfBirth);

            employee.setPosition(position);
            employee.setRank(rank);
            employee.setDepartment(department);

            employee.setDateOfSign(dateOfSign);
            employee.setContractPeriod(contractPeriod);

            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            employee.setPicture(file.getOriginalFilename());

            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            employeeService.addEmployee(employee);
        }

        return "redirect:/employees";
    }

    @GetMapping(value = "/details/{id}")
    public String details(Model model, @PathVariable(name = "id") int id) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employee);

        List<Position> positions = positionService.getPositions();
        model.addAttribute("positions", positions);

        List<Rank> ranks = rankService.getRanks();
        model.addAttribute("ranks", ranks);

        List<Department> departments = departmentService.getDepartments();
        model.addAttribute("departments", departments);

        return "employeesDetails";
    }

    @PostMapping(value = "/details")
    public String updateEmployee(
            @RequestParam(name = "id", defaultValue = "0") int id,
            @RequestParam(name = "personal_number", defaultValue = "0") int personalNumber,
            @RequestParam(name = "second_name", defaultValue = " ") String secondName,
            @RequestParam(name = "first_name", defaultValue = " ") String firstName,
            @RequestParam(name = "third_name", defaultValue = " ") String thirdName,
            @RequestParam(name = "date_of_birth")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam(name = "position", defaultValue = "0") int positionId,
            @RequestParam(name = "rank", defaultValue = "0") int rankId,
            @RequestParam(name = "department", defaultValue = "0") int departmentId,
            @RequestParam(name = "date_of_sign")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfSign,
            @RequestParam(name = "contract_period") int contractPeriod) {

        Employee employee = employeeService.getEmployee(id);

        Position position = positionService.getPosition(positionId);
        Rank rank = rankService.getRank(rankId);
        Department department = departmentService.getDepartment(departmentId);

        if (employee != null && position != null && rank != null && department != null) {
            employee.setPersonalNumber(personalNumber);
            employee.setSecondName(secondName);
            employee.setFirstName(firstName);
            employee.setThirdName(thirdName);
            employee.setDateOfBirth(dateOfBirth);

            employee.setPosition(position);
            employee.setRank(rank);
            employee.setDepartment(department);

            employee.setDateOfSign(dateOfSign);
            employee.setContractPeriod(contractPeriod);

            employeeService.update(id, employee);
        }

        return "redirect:/employees";
    }

    @PostMapping(value = "/deleteEmployee")
    public String deleteEmployee(@RequestParam(name = "id", defaultValue = "0") int id) {
        Employee employee = employeeService.getEmployee(id);

        if (employee != null) {
            employeeService.delete(employee);
        }

        return "redirect:/employees";
    }

    @GetMapping("/excelExport")
    public void excelExport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        //Написание названия эксель документа
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Employee> employeeList = employeeService.getEmployees();
        ExcelExporter excelExporter = new ExcelExporter(employeeList);
        excelExporter.export(response);
    }

    @PostMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Employee> employees = employeeService.searchEmployeeByFirstName(query);
        employees.addAll(employeeService.searchEmployeeBySecondName(query));
        employees.addAll(employeeService.searchEmployeeByThirdName(query));
        List<Rank> ranks = rankService.searchRank(query);
        List<Position> positions = positionService.searchPosition(query);
        List<Department> departments = departmentService.searchDepartment(query);

        model.addAttribute("employees", employees);
        model.addAttribute("positions", positions);
        model.addAttribute("ranks", ranks);
        model.addAttribute("departments", departments);

        return "searchResults";
        //return "redirect:/employees";
    }


    @GetMapping("/search")
    public String searchPage() {
        return "searchResults";
        //return "employees";
    }
}