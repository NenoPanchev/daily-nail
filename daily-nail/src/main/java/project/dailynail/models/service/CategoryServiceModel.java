package project.dailynail.models.service;

import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;

import java.util.Set;

public class CategoryServiceModel extends BaseServiceModel {
    private CategoryNameEnum categoryName;
    private Set<SubcategoryEntity> subcategories;

    public CategoryServiceModel() {
    }

    public CategoryNameEnum getCategoryName() {
        return categoryName;
    }

    public CategoryServiceModel setCategoryName(CategoryNameEnum categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Set<SubcategoryEntity> getSubcategories() {
        return subcategories;
    }

    public CategoryServiceModel setSubcategories(Set<SubcategoryEntity> subcategories) {
        this.subcategories = subcategories;
        return this;
    }
}
