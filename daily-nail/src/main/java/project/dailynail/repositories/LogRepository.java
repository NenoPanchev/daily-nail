package project.dailynail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.LogEntity;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, String> {
}
