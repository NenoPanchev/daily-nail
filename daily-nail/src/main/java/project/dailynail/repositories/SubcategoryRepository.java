package project.dailynail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.SubcategoryNameEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, String> {
    Optional<SubcategoryEntity> findBySubcategoryName (SubcategoryNameEnum subcategoryNameEnum);
    List<SubcategoryEntity> findAllBySubcategoryNameIn(SubcategoryNameEnum... subcategoryName);
    List<SubcategoryEntity> findAllByCategory_CategoryName(CategoryNameEnum categoryNameEnum);
}
