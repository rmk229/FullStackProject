package kz.yermek.testproject.controllers;

import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.models.Rank;
import kz.yermek.testproject.services.EmployeeService;
import kz.yermek.testproject.services.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ranks")
@RequiredArgsConstructor
public class RankController {
    private final EmployeeService employeeService;
    private final RankService rankService;

    @GetMapping
    public String getAllRanks(Model model) {
        List<Rank> ranks = rankService.getRanks();
        model.addAttribute("ranks", ranks);

        return "ranks";
    }

    @GetMapping("/add")
    public String getForm() {
        return "ranksAdd";
    }

    @PostMapping("/add")
    public String addRank(@RequestParam(name = "rank_name") String rankName) {
        if (rankName != null) {
            Rank rank = new Rank();
            rank.setRankName(rankName);
            rankService.addRank(rank);
        }
        return "redirect:/ranks";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable(name = "id") int id) {
        Rank rank = rankService.getRank(id);
        model.addAttribute("rank", rank);
        return "ranksDetails";
    }

    @PostMapping("/details")
    public String updateRank(@RequestParam(name = "id", defaultValue = "0") int id,
                             @RequestParam(name = "rank") String rankName) {
        Rank rank = rankService.getRank(id);
        if (rank != null && rankName != null) {
            rank.setRankName(rankName);
            rankService.update(rank);
        }

        return "redirect:/ranks";
    }

    @PostMapping(value = "/deleteRank")
    public String deleteRank(@RequestParam(name = "id", defaultValue = "0") int id) {
        Rank rank = rankService.getRank(id);

        List<Employee> employees = employeeService.getEmployees();
        for (Employee employee: employees) {
            if (employee.getRank().getId() == id) {
                return "redirect:/ranks";
            }
        }

        if (rank != null) {
            rankService.delete(rank);
        }

        return "redirect:/ranks";
    }
}
