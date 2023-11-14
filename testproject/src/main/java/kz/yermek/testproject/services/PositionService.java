package kz.yermek.testproject.services;

import kz.yermek.testproject.models.Employee;
import kz.yermek.testproject.models.Position;

import java.util.List;


public interface PositionService {
    List<Position> getPositions();
    Position addPosition(Position position);
    Position update(Position position);
    Position getPosition(int id);
    void delete(Position position);
    List<Position> searchPosition(String query);

}
