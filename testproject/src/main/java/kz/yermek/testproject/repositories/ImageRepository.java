package kz.yermek.testproject.repositories;

import kz.yermek.testproject.models.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Images, Integer> {
    Images getImagesById(int id);
}
