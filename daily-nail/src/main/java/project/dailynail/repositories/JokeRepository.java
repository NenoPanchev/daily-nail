package project.dailynail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.JokeEntity;

@Repository
public interface JokeRepository extends JpaRepository<JokeEntity, String> {
    @Query(value = "SELECT j.text FROM jokes AS j " +
            "ORDER BY j.created DESC " +
            "LIMIT 1", nativeQuery = true)
    String findLatestJoke();
}
