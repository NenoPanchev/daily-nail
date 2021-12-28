package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.entities.CategoryEntity;
import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.SubcategoryNameEnum;
import project.dailynail.models.service.CategoryServiceSeedModel;
import project.dailynail.models.service.SubcategoryServiceModel;
import project.dailynail.repositories.SubcategoryRepository;
import project.dailynail.services.CategoryService;
import project.dailynail.services.SubcategoryService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.subcategoryRepository = subcategoryRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    public void seedSubcategories() {
        if (subcategoryRepository.count() > 0) {
            return;
        }

        Arrays.stream(SubcategoryNameEnum.values())
                .forEach(en -> {
                    SubcategoryEntity subcategoryEntity = new SubcategoryEntity()
                            .setSubcategoryName(en);
                    subcategoryRepository.save(subcategoryEntity);
                });
        attachCategoryToSubcategories(categoryService.findByCategoryName(CategoryNameEnum.SPORTS),
                subcategoryRepository.findAllBySubcategoryNameIn(SubcategoryNameEnum.FOOTBALL,
                        SubcategoryNameEnum.VOLLEYBALL,
                        SubcategoryNameEnum.TENNIS,
                        SubcategoryNameEnum.OTHER));
    }

    private void attachCategoryToSubcategories(CategoryServiceSeedModel categoryServiceSeedModel, List<SubcategoryEntity> subcategoryEntities) {
        subcategoryEntities
                .forEach(subcategoryEntity -> {
                    subcategoryEntity
                            .setCategory(modelMapper.map(categoryServiceSeedModel, CategoryEntity.class));
                    subcategoryRepository.save(subcategoryEntity);
                });
    }

    @Override
    public SubcategoryServiceModel findBySubcategoryNameEnum(SubcategoryNameEnum subcategoryNameEnum) {
        return subcategoryRepository.findBySubcategoryName(subcategoryNameEnum)
                .map(entity -> modelMapper.map(entity, SubcategoryServiceModel.class))
                .orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public SubcategoryServiceModel findBySubcategoryNameStr(String subcategoryName) {
        return subcategoryRepository.findBySubcategoryName(SubcategoryNameEnum.valueOf(subcategoryName))
                .map(entity -> modelMapper.map(entity, SubcategoryServiceModel.class))
                .orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public List<SubcategoryServiceModel> findAllBySubcategoryNameIn(SubcategoryNameEnum... subcategoryNameEnums) {
        return subcategoryRepository
                .findAllBySubcategoryNameIn(subcategoryNameEnums)
                .stream()
                .map(entity -> modelMapper.map(entity, SubcategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryServiceModel> findAllByCategoryName(CategoryNameEnum categoryNameEnum) {
        return subcategoryRepository
                .findAllByCategory_CategoryName(categoryNameEnum)
                .stream()
                .map(entity -> modelMapper.map(entity, SubcategoryServiceModel.class))
                .collect(Collectors.toList());
    }


}
