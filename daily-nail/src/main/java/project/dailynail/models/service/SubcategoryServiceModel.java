package project.dailynail.models.service;

import project.dailynail.models.entities.CategoryEntity;
import project.dailynail.models.entities.enums.SubcategoryNameEnum;

public class SubcategoryServiceModel extends BaseServiceModel {
    private SubcategoryNameEnum subcategoryName;
    private CategoryEntity category;

    public SubcategoryServiceModel() {
    }

    public SubcategoryNameEnum getSubcategoryName() {
        return subcategoryName;
    }

    public SubcategoryServiceModel setSubcategoryName(SubcategoryNameEnum subcategoryName) {
        this.subcategoryName = subcategoryName;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public SubcategoryServiceModel setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }
}
