package project.dailynail.services;

import project.dailynail.models.entities.enums.SubcategoryNameEnum;
import project.dailynail.models.service.SubcategoryServiceModel;

import java.util.List;

public interface SubcategoryService {

    void seedSubcategories();
    SubcategoryServiceModel findBySubcategoryNameEnum(SubcategoryNameEnum subcategoryNameEnum);
    List<SubcategoryServiceModel> findAllBySubcategoryNameIn(SubcategoryNameEnum... subcategoryNameEnums);

}
