package kz.yermek.testproject.controllers;

import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.models.Position;
import kz.yermek.testproject.services.EmployeeService;
import kz.yermek.testproject.services.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

    private final EmployeeService employeeService;
    private final PositionService positionService;

    @GetMapping
    public String getAllPositions(Model model) {
        List<Position> positions = positionService.getPositions();
        model.addAttribute("positions", positions);

        return "positions";
    }

    @GetMapping("/add")
    public String getForm() {
        return "positionsAdd";
    }

    @PostMapping("/add")
    public String addPosition(@RequestParam(name = "position_name") String positionName) {
        if (positionName != null) {
            Position position = new Position();
            position.setPositionName(positionName);
            positionService.addPosition(position);
        }
        return "redirect:/positions";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable(name = "id") int id) {
        Position position = positionService.getPosition(id);
        model.addAttribute("position", position);
        return "positionsDetails";
    }

    @PostMapping("/details")
    public String updatePosition(@RequestParam(name = "id", defaultValue = "0") int id,
                                 @RequestParam(name = "position") String positionName) {
        Position position = positionService.getPosition(id);
        if (position != null && positionName != null) {
            position.setPositionName(positionName);
            positionService.update(position);
        }

        return "redirect:/positions";
    }

    @PostMapping(value = "/deletePositions")
    public String deletePosition(@RequestParam(name = "id", defaultValue = "0") int id) {
        Position position = positionService.getPosition(id);

        List<Employee> employees = employeeService.getEmployees();
        for (Employee employee : employees) {
            if (employee.getPosition().getId() == id) {
                return "redirect:/positions";
            }
        }

        if (position != null) {
            positionService.delete(position);
        }

        return "redirect:/positions";
    }
}