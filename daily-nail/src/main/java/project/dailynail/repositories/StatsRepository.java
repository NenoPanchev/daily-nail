package project.dailynail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.StatsEntity;

@Repository
public interface StatsRepository extends JpaRepository<StatsEntity, String> {

}
