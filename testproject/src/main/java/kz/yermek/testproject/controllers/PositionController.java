package kz.yermek.testproject.controllers;

import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.models.Position;
import kz.yermek.testproject.services.EmployeeService;
import kz.yermek.testproject.services.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PositionController {

    private final EmployeeService employeeService;
    private final PositionService positionService;

    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        List<Position> positions = positionService.getPositions();
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @GetMapping("/add")
    public ResponseEntity<Position> getForm() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Position> createPosition(@RequestBody Position position) throws URISyntaxException {
        Position savedPosition = positionService.save(position);
        return ResponseEntity.created(new URI("/positions/" +
                savedPosition.getId())).body(savedPosition);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Position> details(@PathVariable int id) {
        Position position = positionService.getPosition(id);

        if (position != null) {
            return new ResponseEntity<>(position, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/details/{id}")
    public ResponseEntity<Position> updatePosition(@RequestBody Position newPosition,  @PathVariable int id) {
        Position position = positionService.getPosition(id);
        if (position != null && newPosition != null) {
            position.setPositionName(newPosition.getPositionName());
            positionService.update(position);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Position> deletePosition(@PathVariable int id) {
        Position position = positionService.getPosition(id);
        List<Employee> employees = employeeService.getEmployees();

        for (Employee employee : employees) {
            if (employee.getPosition().getId() == id) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        if (position != null) {
            positionService.delete(position);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}