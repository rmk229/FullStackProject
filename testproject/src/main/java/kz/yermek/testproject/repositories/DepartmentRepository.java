package kz.yermek.testproject.repositories;

import kz.yermek.testproject.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findByDepartmentNameEqualsIgnoreCase(String query);
}
