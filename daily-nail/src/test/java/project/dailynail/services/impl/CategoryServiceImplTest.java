package project.dailynail.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.dailynail.models.entities.CategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.service.CategoryServiceModel;
import project.dailynail.models.service.CategoryServiceSeedModel;
import project.dailynail.models.service.SubcategoryServiceModel;
import project.dailynail.repositories.CategoryRepository;
import project.dailynail.services.CategoryService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private CategoryEntity sports, world;
    private CategoryService serviceToTest;

    @Mock
    private CategoryRepository mockCategoryRepository;

    @BeforeEach
    void setUp() {
        sports = new CategoryEntity()
                .setCategoryName(CategoryNameEnum.SPORTS)
                .setSubcategories(new HashSet<>());

        world = new CategoryEntity()
                .setCategoryName(CategoryNameEnum.WORLD)
                .setSubcategories(new HashSet<>());

        serviceToTest = new CategoryServiceImpl(mockCategoryRepository, new ModelMapper());
    }

    @Test
    void seedCategoriesTest() {
        when(mockCategoryRepository.save(sports))
                .thenReturn(sports);
        CategoryEntity expected = mockCategoryRepository.save(sports);
        serviceToTest.seedCategories();
        assertEquals(expected.getCategoryName().name(), sports.getCategoryName().name());
    }

    @Test
    void findByCategoryNameTest() {
        when(mockCategoryRepository.findByCategoryName(CategoryNameEnum.SPORTS))
                .thenReturn(Optional.of(sports));
        CategoryServiceSeedModel expected = serviceToTest.findByCategoryName(CategoryNameEnum.SPORTS);
        assertEquals(expected.getCategoryName().name(), sports.getCategoryName().name());
    }

    @Test
    void findByCategoryNameStrTest() {
        when(mockCategoryRepository.findByCategoryName(CategoryNameEnum.SPORTS))
                .thenReturn(Optional.of(sports));
        CategoryServiceModel expected = serviceToTest.findByCategoryNameStr("SPORTS");
        CategoryServiceModel actualNull = serviceToTest.findByCategoryNameStr("hello");

        assertEquals(expected.getCategoryName(), sports.getCategoryName());
        assertNull(actualNull);
    }

    @Test
    void getAllCategoriesTest() {
        when(mockCategoryRepository.findAllJoinSubcategories())
                .thenReturn(List.of(sports, world));
        List<String> expected = List.of("Sports", "World");
        List<String> actual = serviceToTest.getAllCategories();
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
    }
}