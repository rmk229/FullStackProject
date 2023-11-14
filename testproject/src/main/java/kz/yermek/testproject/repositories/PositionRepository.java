package kz.yermek.testproject.repositories;

import kz.yermek.testproject.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PositionRepository extends JpaRepository<Position, Integer> {
    List<Position> findByPositionNameEqualsIgnoreCase(String query);
}
