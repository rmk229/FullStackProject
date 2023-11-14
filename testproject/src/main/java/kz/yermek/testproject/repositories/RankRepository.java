package kz.yermek.testproject.repositories;

import kz.yermek.testproject.models.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RankRepository extends JpaRepository<Rank, Integer> {
    List<Rank> findByRankNameEqualsIgnoreCase(String query);
}
