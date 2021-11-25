package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.entities.CategoryEntity;
import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.SubcategoryNameEnum;
import project.dailynail.models.service.CategoryServiceModel;
import project.dailynail.repositories.CategoryRepository;
import project.dailynail.services.CategoryService;
import project.dailynail.services.SubcategoryService;

import java.util.Arrays;
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
    public CategoryServiceModel findByCategoryName(CategoryNameEnum categoryNameEnum) {
        return categoryRepository.findByCategoryName(categoryNameEnum)
                .map(entity -> modelMapper.map(entity, CategoryServiceModel.class))
                .orElseThrow(ObjectNotFoundException::new);
    }
}
