package project.dailynail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.ArticleEntity;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {
}
