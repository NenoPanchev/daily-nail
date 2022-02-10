package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.entities.CategoryEntity;

import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;

import project.dailynail.models.service.CategoryServiceModel;
import project.dailynail.models.service.CategoryServiceSeedModel;
import project.dailynail.repositories.CategoryRepository;
import project.dailynail.services.CategoryService;


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
    public CategoryServiceSeedModel findByCategoryName(CategoryNameEnum categoryNameEnum) {
        return categoryRepository.findByCategoryName(categoryNameEnum)
                .map(entity -> modelMapper.map(entity, CategoryServiceSeedModel.class))
                .orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public CategoryServiceModel findByCategoryNameStr(String categoryName) {
        return categoryRepository.findByCategoryName(CategoryNameEnum.valueOf(categoryName.toUpperCase()))
                .map(entity -> modelMapper.map(entity, CategoryServiceModel.class))
                .orElse(null);
    }

    @Override
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
         categoryRepository
                .findAll()
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
}
