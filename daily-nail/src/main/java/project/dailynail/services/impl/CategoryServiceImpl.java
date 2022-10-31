package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.entities.CategoryEntity;

import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;

import project.dailynail.models.entities.enums.SubcategoryNameEnum;
import project.dailynail.models.service.CategoryServiceModel;
import project.dailynail.models.service.CategoryServiceSeedModel;
import project.dailynail.repositories.CategoryRepository;
import project.dailynail.services.CategoryService;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories() {
        if (categoryRepository.count() > 0) {
            return;
        }

        Arrays.stream(CategoryNameEnum.values())
                .forEach(enm -> {
                    CategoryEntity categoryEntity = new CategoryEntity()
                            .setCategoryName(enm);

                    categoryRepository.save(categoryEntity);
                });
    }

    @Override
    @Transactional
    public CategoryServiceSeedModel findByCategoryName(CategoryNameEnum categoryNameEnum) {
        return categoryRepository.findByCategoryName(categoryNameEnum)
                .map(entity -> modelMapper.map(entity, CategoryServiceSeedModel.class))
                .orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public CategoryServiceModel findByCategoryNameStr(String categoryName) {
        if (!Arrays.stream(CategoryNameEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList())
                .contains(categoryName)) {
            return null;
        }

        return categoryRepository.findByCategoryName(CategoryNameEnum.valueOf(categoryName.toUpperCase()))
                .map(entity -> modelMapper.map(entity, CategoryServiceModel.class))
                .orElse(null);
    }

    @Override
    @Transactional
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        List<CategoryEntity> entities = categoryRepository.findAllJoinSubcategories();
            entities
                .stream()
                .forEach(categoryEntity -> {
                    categories.add(makePascalCase(categoryEntity.getCategoryName().name().replace('_', ' ')));
                    categoryEntity
                            .getSubcategories()
                            .forEach(subcategoryEntity -> categories.add(" - " + makePascalCase(subcategoryEntity.getSubcategoryName().name())));
                });
         return categories;
    }

    private String makePascalCase(String name) {
        return name.charAt(0) + name.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return "CategoryServiceImpl{}";
    }
}
