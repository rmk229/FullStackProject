package kz.yermek.testproject.controllers;

import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.models.Rank;
import kz.yermek.testproject.services.EmployeeService;
import kz.yermek.testproject.services.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/ranks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RankController {

    private final EmployeeService employeeService;
    private final RankService rankService;

    @GetMapping
    public ResponseEntity<List<Rank>> getAllRanks() {
        List<Rank> ranks = rankService.getRanks();
        return new ResponseEntity<>(ranks, HttpStatus.OK);
    }

    @GetMapping("/add")
    public ResponseEntity<Rank> getForm() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Rank> createRank(@RequestBody Rank rank) throws URISyntaxException {
        Rank savedRank = rankService.save(rank);
        return ResponseEntity.created(new URI("/ranks/" +
                savedRank.getId())).body(savedRank);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Rank> details(@PathVariable int id) {
        Rank rank = rankService.getRank(id);

        if (rank != null) {
            return new ResponseEntity<>(rank, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/details/{id}")
    public ResponseEntity<Rank> updateRank(@RequestBody Rank newRank, @PathVariable int id) {
        Rank rank = rankService.getRank(id);
        if (rank != null && newRank != null) {
            rank.setRankName(newRank.getRankName());
            rankService.update(rank);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Rank> deleteRank(@PathVariable int id) {
        Rank rank = rankService.getRank(id);
        List<Employee> employees = employeeService.getEmployees();

        for (Employee employee: employees) {
            if (employee.getRank().getId() == id) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        if (rank != null) {
            rankService.delete(rank);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
