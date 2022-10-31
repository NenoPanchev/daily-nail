package project.dailynail.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.entities.enums.SubcategoryNameEnum;
import project.dailynail.models.service.SubcategoryServiceModel;
import project.dailynail.models.service.UserRoleServiceModel;
import project.dailynail.repositories.SubcategoryRepository;
import project.dailynail.services.CategoryService;
import project.dailynail.services.SubcategoryService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubcategoryServiceImplTest {
    private SubcategoryEntity football, volleyball, tennis, other;
    private SubcategoryService serviceToTest;

    @Mock
    private SubcategoryRepository mockSubcategoryRepository;

    @Mock
    private CategoryService mockCategoryService;

    @BeforeEach
    void setUp() {
        football = new SubcategoryEntity().setSubcategoryName(SubcategoryNameEnum.FOOTBALL);
        volleyball = new SubcategoryEntity().setSubcategoryName(SubcategoryNameEnum.VOLLEYBALL);
        tennis = new SubcategoryEntity().setSubcategoryName(SubcategoryNameEnum.TENNIS);
        other = new SubcategoryEntity().setSubcategoryName(SubcategoryNameEnum.OTHER);

        serviceToTest = new SubcategoryServiceImpl(mockSubcategoryRepository, new ModelMapper(), mockCategoryService);
    }

    @Test
    void seedTest() {
        when(mockSubcategoryRepository.count())
                .thenReturn(0L);
        when(mockSubcategoryRepository.save(football))
                .thenReturn(football);
        SubcategoryEntity fb = mockSubcategoryRepository.save(this.football);
        serviceToTest.seedSubcategories();
        assertEquals(fb.getSubcategoryName(), this.football.getSubcategoryName());
    }

    @Test
    void findBySubcategoryNameEnumTest() {
        when(mockSubcategoryRepository.findBySubcategoryName(football.getSubcategoryName()))
                .thenReturn(Optional.of(football));
        SubcategoryServiceModel expected = serviceToTest.findBySubcategoryNameEnum(football.getSubcategoryName());
        assertEquals(expected.getSubcategoryName().name(), football.getSubcategoryName().name());
    }

    @Test
    void findBySubcategoryNameStrTest() {
        when(mockSubcategoryRepository.findBySubcategoryName(football.getSubcategoryName()))
                .thenReturn(Optional.of(football));
        SubcategoryServiceModel expected = serviceToTest.findBySubcategoryNameStr("FOOTBALL");
        SubcategoryServiceModel actualNull = serviceToTest.findBySubcategoryNameStr("hello");

        assertEquals(expected.getSubcategoryName(), football.getSubcategoryName());
        assertNull(actualNull);
    }

    @Test
    void testFindAllBySubcategoryNameIn() {
        SubcategoryNameEnum[] enums = new SubcategoryNameEnum[]{SubcategoryNameEnum.FOOTBALL, SubcategoryNameEnum.VOLLEYBALL};
        Mockito.when(mockSubcategoryRepository.findAllBySubcategoryNameIn(enums))
                .thenReturn(List.of(football, volleyball));

        List<SubcategoryServiceModel> actual = serviceToTest.findAllBySubcategoryNameIn(enums);

        assertEquals(actual.stream().count(), 2L);
        assertEquals(football.getSubcategoryName(), actual.get(0).getSubcategoryName());
        assertEquals(volleyball.getSubcategoryName(), actual.get(1).getSubcategoryName());
    }

    @Test
    void testFindAllByCategoryName() {
        List<SubcategoryEntity> entities = List.of(football, volleyball, tennis, other);
        when(mockSubcategoryRepository.findAllByCategory_CategoryName(CategoryNameEnum.SPORTS))
                .thenReturn(entities);
        List<SubcategoryServiceModel> actual = serviceToTest.findAllByCategoryName(CategoryNameEnum.SPORTS);

        assertEquals(4, actual.stream().count());
        assertEquals(football.getSubcategoryName(), actual.get(0).getSubcategoryName());
        assertEquals(tennis.getSubcategoryName(), actual.get(2).getSubcategoryName());
    }

    @Test
    void testToString() {
        String expected = "SubcategoryServiceImpl{}";
        String actual = serviceToTest.toString();
        assertEquals(expected, actual);
    }
}