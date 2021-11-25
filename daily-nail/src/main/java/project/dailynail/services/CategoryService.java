package project.dailynail.services;

import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.service.CategoryServiceModel;

public interface CategoryService {
    void seedCategories();
    CategoryServiceModel findByCategoryName(CategoryNameEnum categoryNameEnum);
}
