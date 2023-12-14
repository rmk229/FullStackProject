package kz.yermek.testproject.services;

import kz.yermek.testproject.models.Rank;

import java.util.List;


public interface RankService {
    List<Rank> getRanks();
    Rank addRank(Rank rank);
    Rank update(Rank rank);
    Rank getRank(int id);
    void  delete(Rank rank);
    Rank save(Rank rank);
    List<Rank> searchRank(String query);
}
