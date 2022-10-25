package project.dailynail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.CategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    Optional<CategoryEntity> findByCategoryName(CategoryNameEnum categoryNameEnum);

    @Query("SELECT c FROM CategoryEntity c ")
    List<CategoryEntity> findAllJoinSubcategories();
}
