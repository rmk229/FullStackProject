package kz.yermek.testproject.services.impl;

import kz.yermek.testproject.models.Position;
import kz.yermek.testproject.repositories.PositionRepository;
import kz.yermek.testproject.services.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    @Override
    public Position addPosition(Position position) {
        return positionRepository.save((position));
    }

    @Override
    public Position update(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public Position getPosition(int id) {
        return positionRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(Position position) {
        positionRepository.delete(position);
    }

    @Override
    public List<Position> searchPosition(String query) {
        return positionRepository.findByPositionNameEqualsIgnoreCase(query);
    }
}
