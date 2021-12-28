package project.dailynail.services;

import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.service.CategoryServiceModel;
import project.dailynail.models.service.CategoryServiceSeedModel;

import java.util.List;

public interface CategoryService {
    void seedCategories();
    CategoryServiceSeedModel findByCategoryName(CategoryNameEnum categoryNameEnum);
    CategoryServiceModel findByCategoryNameStr(String categoryName);

    List<String> getAllCategories();
}
