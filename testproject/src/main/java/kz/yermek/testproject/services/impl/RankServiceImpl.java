package kz.yermek.testproject.services.impl;

import kz.yermek.testproject.models.Rank;
import kz.yermek.testproject.repositories.RankRepository;
import kz.yermek.testproject.services.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {

    private final RankRepository rankRepository;

    @Override
    public List<Rank> getRanks() {
        return rankRepository.findAll();
    }

    @Override
    public Rank addRank(Rank rank) {
        return rankRepository.save(rank);
    }

    @Override
    public Rank update(Rank rank) {
        return rankRepository.save(rank);
    }

    @Override
    public Rank getRank(int id) {
        return rankRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(Rank rank) {
        rankRepository.delete(rank);
    }

    @Override
    public List<Rank> searchRank(String query) {
        return rankRepository.findByRankNameEqualsIgnoreCase(query);
    }
}
