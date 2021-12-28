package project.dailynail.services;

import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.SubcategoryNameEnum;
import project.dailynail.models.service.SubcategoryServiceModel;

import java.util.List;

public interface SubcategoryService {

    void seedSubcategories();
    SubcategoryServiceModel findBySubcategoryNameEnum(SubcategoryNameEnum subcategoryNameEnum);
    SubcategoryServiceModel findBySubcategoryNameStr(String  subcategoryName);
    List<SubcategoryServiceModel> findAllBySubcategoryNameIn(SubcategoryNameEnum... subcategoryNameEnums);
    List<SubcategoryServiceModel> findAllByCategoryName(CategoryNameEnum categoryNameEnum);
}
