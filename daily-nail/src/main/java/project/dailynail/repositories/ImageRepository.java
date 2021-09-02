package project.dailynail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, String> {
}
