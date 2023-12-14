package kz.yermek.testproject.repositories;


import kz.yermek.testproject.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByFirstNameEqualsIgnoreCase(String query);
    List<Employee> findBySecondNameEqualsIgnoreCase(String query);
    List<Employee> findByThirdNameEqualsIgnoreCase(String query);
}
