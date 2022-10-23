package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import project.dailynail.constants.GlobalConstants;
import project.dailynail.models.binding.ArticleEditBindingModel;
import project.dailynail.models.binding.ArticleSearchBindingModel;
import project.dailynail.models.dtos.json.ArticleEntityExportDto;
import project.dailynail.models.entities.*;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.service.*;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.models.view.*;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.services.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    private ArticleEntity sportArticle, worldArticle, entertainmentArticle;
    private UserEntity admin, editor, user;
    private CommentEntity first, second;
    private CategoryEntity sportsCategory, worldCategory, entertainmentCategory;
    private UserRoleEntity adminRole, editorRole, userRole;
    private ArticleService serviceToTest;
    private static final Integer ARTICLES_PER_PAGE = 6;
    private CategoryServiceModel categoryServiceModel = new CategoryServiceModel()
            .setCategoryName(CategoryNameEnum.WORLD)
            .setSubcategoryNames(new HashSet<>());
    private CategoryServiceSeedModel categoryServiceSeedModel = new CategoryServiceSeedModel()
            .setCategoryName(CategoryNameEnum.WORLD)
            .setSubcategories(new HashSet<>());


    @Mock
    private ArticleRepository mockArticleRepository;
    @Mock
    private ServiceLayerValidationUtil mockServiceLayerValidationUtil;
    @Mock
    private UserService mockUserService;
    @Mock
    private CloudinaryService mockCloudinaryService;
    @Mock
    private CategoryService mockCategoryService;
    @Mock
    private SubcategoryService mockSubcategoryService;
    @Mock
    private TopArticlesServiceImpl mockTopArticlesService;

    @BeforeEach
    void setUp() {
        serviceToTest = new ArticleServiceImpl(mockArticleRepository, new ModelMapper(), mockServiceLayerValidationUtil, new Gson(),
                mockUserService, mockCloudinaryService, mockCategoryService, mockSubcategoryService,
                mockTopArticlesService);
        // UserRoles
        adminRole = new UserRoleEntity().setRole(Role.ADMIN);
        editorRole = new UserRoleEntity().setRole(Role.EDITOR);
        userRole = new UserRoleEntity().setRole(Role.USER);

        // Categories
        sportsCategory = new CategoryEntity()
                .setCategoryName(CategoryNameEnum.SPORTS)
                .setSubcategories(new HashSet<>());
        worldCategory = new CategoryEntity()
                .setCategoryName(CategoryNameEnum.WORLD)
                .setSubcategories(new HashSet<>());
        entertainmentCategory = new CategoryEntity()
                .setCategoryName(CategoryNameEnum.ENTERTAINMENT)
                .setSubcategories(new HashSet<>());

        // Users
        admin = new UserEntity()
                .setEmail("admin@admin.com")
                .setFullName("Admin Admin")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(adminRole, editorRole, userRole));
        admin.setId("1");
        editor = new UserEntity()
                .setEmail("editor@editor.com")
                .setFullName("Editor Editor")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(editorRole, userRole));
        editor.setId("2");
        user = new UserEntity()
                .setEmail("user@user.com")
                .setFullName("User User")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(userRole));
        user.setId("3");

        // Comments
        first = (CommentEntity) new CommentEntity().setId("1");
        first.setText("111")
                .setTimePosted(LocalDateTime.now())
                .setLikes(1)
                .setDislikes(1)
                .setAuthor(editor)
                .setArticle(null);
        second = (CommentEntity) new CommentEntity().setId("2");
        second.setText("222")
                .setTimePosted(LocalDateTime.now())
                .setLikes(1)
                .setDislikes(1)
                .setAuthor(user)
                .setArticle(null);

        // Articles
        sportArticle = new ArticleEntity()
                .setAuthor(editor)
                .setActivated(true)
                .setCategory(sportsCategory)
                .setText("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
                        "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                .setComments(List.of(first, second))
                .setCreated(LocalDateTime.now())
                .setPosted(LocalDateTime.now())
                .setDisabledComments(false)
                .setSubcategory(null)
                .setSeen(11)
                .setImageUrl("sportsUrl")
                .setTitle("Sports Title")
                .setTop(true);
                sportArticle.setId("1");

        worldArticle = new ArticleEntity()
                .setAuthor(admin)
                .setActivated(true)
                .setCategory(worldCategory)
                .setText("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" +
                        "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww")
                .setComments(new ArrayList<>())
                .setCreated(LocalDateTime.now())
                .setPosted(LocalDateTime.now())
                .setDisabledComments(false)
                .setSubcategory(null)
                .setSeen(22)
                .setImageUrl("worldUrl")
                .setTitle("World Title")
                .setTop(true);
        worldArticle.setId("2");

        entertainmentArticle = new ArticleEntity()
                .setAuthor(editor)
                .setActivated(true)
                .setCategory(entertainmentCategory)
                .setText("sssssssssssssssssssssseeeeeeeeeeeeeeeeeeeeeeeeeeeeesssssssssssssssssssssssssssssssss" +
                        "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                .setComments(new ArrayList<>())
                .setCreated(LocalDateTime.now())
                .setPosted(LocalDateTime.now())
                .setDisabledComments(false)
                .setSubcategory(null)
                .setSeen(33)
                .setImageUrl("entertainmentUrl")
                .setTitle("Entertainment Title")
                .setTop(true);
        entertainmentArticle.setId("3");
    }

    @Test
    void createArticleTest() throws IOException {
        lenient().doNothing().when(mockServiceLayerValidationUtil).validate(isA(ArticleCreateServiceModel.class));
        UserServiceModel principal = new UserServiceModel()
                .setEmail(admin.getEmail())
                .setFullName(admin.getFullName())
                .setPassword(admin.getPassword());
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        ArticleCreateServiceModel createServiceModel = new ArticleCreateServiceModel()
                .setImageUrl("")
                .setImageFile(mockFile)
                .setTitle(worldArticle.getTitle())
                .setText(worldArticle.getText())
                .setPosted(worldArticle.getPosted())
                .setCategoryName("World")
                .setDisabledComments("No")
                .setTop("No");
        CategoryServiceModel categoryServiceModel = new CategoryServiceModel()
                .setCategoryName(CategoryNameEnum.WORLD)
                .setSubcategoryNames(new HashSet<>());
        CategoryServiceSeedModel categoryServiceSeedModel = new CategoryServiceSeedModel()
                .setCategoryName(CategoryNameEnum.WORLD)
                .setSubcategories(new HashSet<>());
        when(mockUserService.getPrincipal())
                .thenReturn(principal);
        when(mockCloudinaryService.uploadImage(mockFile))
                .thenReturn(createServiceModel.getImageUrl());
//        when(mockSubcategoryService.findBySubcategoryNameStr(createServiceModel.getCategoryName()))
//                .thenReturn(null);
        when(mockCategoryService.findByCategoryNameStr("WORLD"))
                .thenReturn(categoryServiceModel);
        when(mockCategoryService.findByCategoryName(categoryServiceSeedModel.getCategoryName()))
                .thenReturn(categoryServiceSeedModel);
        when(mockArticleRepository.save(worldArticle))
                .thenReturn(worldArticle);
        ArticleEntity expected = mockArticleRepository.save(worldArticle);
        serviceToTest.createArticle(createServiceModel);
        verify(mockServiceLayerValidationUtil, times(1)).validate(createServiceModel);
        assertEquals(expected.getTitle(), worldArticle.getTitle());
    }

    @Test
    void getAllArticlesForAdminPanelTest() {
        Pageable pageable = PageRequest.of(0, ARTICLES_PER_PAGE);
        Page<ArticleEntity> entities = new PageImpl<ArticleEntity>(List.of(sportArticle, worldArticle, entertainmentArticle));
        when(mockArticleRepository.findAllByOrderByCreatedDesc(pageable))
                .thenReturn(entities);
        ArticlesPageViewModel expected = serviceToTest.getAllArticlesForAdminPanel(1);
        assertEquals(expected.getTotalElements(), 3);
        assertEquals(expected.getTotalPages(), 1);
        assertEquals(expected.getContent().get(1).getTitle(), worldArticle.getTitle());
    }

    @Test
    void getAllArticlesForAdminPanelWithoutParamTest() {
        Pageable pageable = PageRequest.of(0, ARTICLES_PER_PAGE);
        Page<ArticleEntity> entities = new PageImpl<ArticleEntity>(List.of(sportArticle, worldArticle, entertainmentArticle));
        when(mockArticleRepository.findAllByOrderByCreatedDesc(pageable))
                .thenReturn(entities);
        ArticlesPageViewModel expected = serviceToTest.getAllArticlesForAdminPanel();
        assertEquals(expected.getTotalElements(), 3);
        assertEquals(expected.getTotalPages(), 1);
        assertEquals(expected.getContent().get(1).getTitle(), worldArticle.getTitle());
    }

    @Test
    void getAllArticlesByCategoryWithParamTest() {
        Pageable pageable = PageRequest.of(0, ARTICLES_PER_PAGE);
        LocalDateTime now = LocalDateTime.now();
        Page<ArticleEntity> entities = new PageImpl<ArticleEntity>(List.of(worldArticle));
        when(mockCategoryService.findByCategoryNameStr("WORLD"))
                .thenReturn(categoryServiceModel);
//        when(mockCategoryService.findByCategoryName(categoryServiceSeedModel.getCategoryName()))
//                .thenReturn(categoryServiceSeedModel);
        when(mockSubcategoryService.findBySubcategoryNameStr("WORLD"))
                .thenReturn(null);
        when(mockArticleRepository.findAllByCategoryNameOrderByPostedDesc(categoryServiceModel.getCategoryName(), now, pageable))
                .thenReturn(entities);
        ArticlePageVModel expected = serviceToTest.getAllArticlesByCategory("WORLD", now, 1);
        assertEquals(expected.getTotalElements(), 1);
        assertEquals(expected.getTotalPages(), 1);
        assertEquals(expected.getContent().get(0).getTitle(), worldArticle.getTitle());
    }

    @Test
    void getAllArticlesByCategoryWithoutParamTest() {
        Pageable pageable = PageRequest.of(0, ARTICLES_PER_PAGE);
        LocalDateTime now = LocalDateTime.now();
        Page<ArticleEntity> entities = new PageImpl<ArticleEntity>(List.of(worldArticle));
        when(mockCategoryService.findByCategoryNameStr("WORLD"))
                .thenReturn(categoryServiceModel);
//        when(mockCategoryService.findByCategoryName(categoryServiceSeedModel.getCategoryName()))
//                .thenReturn(categoryServiceSeedModel);
        when(mockSubcategoryService.findBySubcategoryNameStr("WORLD"))
                .thenReturn(null);
        when(mockArticleRepository.findAllByCategoryNameOrderByPostedDesc(categoryServiceModel.getCategoryName(), now, pageable))
                .thenReturn(entities);
        ArticlePageVModel expected = serviceToTest.getAllArticlesByCategory("WORLD", now);
        assertEquals(expected.getTotalElements(), 1);
        assertEquals(expected.getTotalPages(), 1);
        assertEquals(expected.getContent().get(0).getTitle(), worldArticle.getTitle());
    }

    @Test
    void getFilteredArticlesTest() {
        Pageable pageable = PageRequest.of(0, ARTICLES_PER_PAGE);
        ArticleSearchBindingModel articleSearchBindingModel = new ArticleSearchBindingModel()
                .setArticleStatus("Activated")
                .setCategory("WORLD")
                .setKeyWord("www")
                .setAuthorName("Admin")
                .setTimePeriod("Today")
                .setPage(1);
        Page<String> articleIds = new PageImpl<>(List.of(worldArticle.getId()));

        when(mockArticleRepository.findAllArticleIdBySearchFilter(articleSearchBindingModel.getKeyWord(), articleSearchBindingModel.getCategory(),
                articleSearchBindingModel.getAuthorName(), "true", 0,pageable))
                .thenReturn(articleIds);
        when(mockArticleRepository.findAllByIdInJoinWithComments(articleIds.getContent()))
                .thenReturn(List.of(worldArticle));

        ArticlesPageViewModel expected = serviceToTest.getFilteredArticles(articleSearchBindingModel);
        assertEquals(expected.getTotalElements(), 1);
        assertEquals(expected.getTotalPages(), 1);
        assertEquals(expected.getContent().get(0).getTitle(), worldArticle.getTitle());
    }

    @Test
    void getArticleEditBindingModelByIdTest() {
        when(mockArticleRepository.findById(worldArticle.getId()))
                .thenReturn(Optional.of(worldArticle));
        ArticleEditBindingModel expected = serviceToTest.getArticleEditBindingModelById(worldArticle.getId());
        assertEquals(expected.getTitle(), worldArticle.getTitle());
    }

    @Test
    void deleteArticleTest() {
        lenient().doNothing().when(mockArticleRepository).deleteById(worldArticle.getId());
        lenient().doNothing().when(mockTopArticlesService).remove(worldArticle.getId());
        serviceToTest.deleteArticle(worldArticle.getId());
        verify(mockArticleRepository, times(1)).deleteById(worldArticle.getId());
        verify(mockTopArticlesService, times(1)).remove(worldArticle.getId());
    }

    @Test
    void editArticleTest() throws IOException {
        when(mockArticleRepository.findById(entertainmentArticle.getId()))
                .thenReturn(Optional.of(entertainmentArticle));
        MultipartFile mockImageFile = Mockito.mock(MultipartFile.class);
        ArticleEditBindingModel articleEditBindingModel = new ArticleEditBindingModel()
                .setId(entertainmentArticle.getId())
                .setCategoryName("Technology")
                .setTitle("Technology Title")
                .setText("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt" +
                        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
                .setImageUrl("")
                .setDisabledComments("Yes")
                .setActivated(false)
                .setPosted(LocalDateTime.now())
                .setTop("No")
                .setImageFile(mockImageFile);

        lenient().doNothing().when(mockTopArticlesService).remove(entertainmentArticle.getId());
        when(mockArticleRepository.save(entertainmentArticle))
                .thenReturn(entertainmentArticle);
        when(mockImageFile.getContentType())
                .thenReturn("application/octet-stream");
        serviceToTest.editArticle(articleEditBindingModel);
        verify(mockTopArticlesService, times(1)).remove(entertainmentArticle.getId());
    }

    @Test
    void getNewestArticleByCategoryNameTest() {
        LocalDateTime now = LocalDateTime.now();
        when(mockArticleRepository.findFirstByCategoryNameOrderByPostedDesc(worldCategory.getCategoryName(), now))
                .thenReturn(worldArticle.getId());
        when(mockArticleRepository.findById(worldArticle.getId()))
                .thenReturn(Optional.of(worldArticle));
        ArticlePreViewModel expected = serviceToTest.getNewestArticleByCategoryName(worldCategory.getCategoryName(), now);
        assertEquals(expected.getId(), worldArticle.getId());
        assertEquals(expected.getTitle(), worldArticle.getTitle());
        assertEquals(expected.getUrl(), worldArticle.getUrl());
    }

    @Test
    void getFourArticlesByCategoryNameTest() {
        LocalDateTime now = LocalDateTime.now();
        when(mockArticleRepository.findFourByCategoryNameOrderByPostedDesc(worldCategory.getCategoryName(), now))
                .thenReturn(List.of(worldArticle.getId()));
        when(mockArticleRepository.findAllByIdIn(List.of(worldArticle.getId())))
                .thenReturn(List.of(worldArticle));
        List<ArticlePreViewModel> expected = serviceToTest.getFourArticlesByCategoryName(worldCategory.getCategoryName(), now);
        assertEquals(expected.get(0).getId(), worldArticle.getId());
        assertEquals(expected.get(0).getTitle(), worldArticle.getTitle());
        assertEquals(expected.get(0).getUrl(), worldArticle.getUrl());
        assertEquals(expected.size(), 1);
    }

    @Test
    void getLatestNArticlesTest() {
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArticleEntity> entities = new PageImpl<>(List.of(sportArticle, worldArticle, entertainmentArticle));
        when(mockArticleRepository.findLatestArticles(now, pageable))
                .thenReturn(entities);
        List<ArticlePreViewModel> expected = serviceToTest.getLatestFiveArticles(now);
        assertEquals(expected.get(0).getId(), sportArticle.getId());
        assertEquals(expected.get(1).getTitle(), worldArticle.getTitle());
        assertEquals(expected.get(2).getUrl(), entertainmentArticle.getUrl());
        assertEquals(expected.size(), 3);


        Pageable pageable9 = PageRequest.of(0, 9);
        Page<ArticleEntity> entities9 = new PageImpl<>(List.of(sportArticle, worldArticle, entertainmentArticle));
        when(mockArticleRepository.findLatestArticles(now, pageable9))
                .thenReturn(entities9);

        List<ArticlePreViewModel> expected9 = serviceToTest.getLatestNineArticles(now);
        assertEquals(expected9.get(0).getId(), sportArticle.getId());
        assertEquals(expected9.get(1).getTitle(), worldArticle.getTitle());
        assertEquals(expected9.get(2).getUrl(), entertainmentArticle.getUrl());
        assertEquals(expected9.size(), 3);
    }

    @Test
    void getFiveMostPopularTest() {
        Pageable pageable = PageRequest.of(0, 5);
        List<ArticleEntity> entities = List.of(entertainmentArticle, worldArticle, sportArticle);
        when(mockArticleRepository.getFiveMostPopular(pageable))
                .thenReturn(entities);
        List<ArticlePreViewModel> expected = serviceToTest.getFiveMostPopular();
        assertEquals(expected.get(0).getId(), entertainmentArticle.getId());
        assertEquals(expected.get(1).getTitle(), worldArticle.getTitle());
        assertEquals(expected.get(2).getUrl(), sportArticle.getUrl());
        assertEquals(expected.size(), 3);
    }

    @Test
    void setTopFalseTest() {
        lenient().doNothing().when(mockArticleRepository).updateTop(worldArticle.getId(), false);
        lenient().doNothing().when(mockArticleRepository).updateTop(worldArticle.getId(), true);
        serviceToTest.setTopFalse(worldArticle.getId());
        serviceToTest.setTopTrue(worldArticle.getId());
        verify(mockArticleRepository, times(1)).updateTop(worldArticle.getId(), false);
    }

    @Test
    void getTopArticlesTest() {
        LocalDateTime now = LocalDateTime.now();
        List<String> ids = new ArrayList<>();
        ids.add(entertainmentArticle.getId());
        ids.add(worldArticle.getId());
        ids.add(sportArticle.getId());
        ArrayDeque<String> arrayDeque = new ArrayDeque<String>(ids);
        when(mockArticleRepository.findAllByIdIn(ids))
                .thenReturn(List.of(sportArticle, worldArticle, entertainmentArticle));
        when(mockTopArticlesService.getTopArticlesIds())
                .thenReturn(arrayDeque);
        List<ArticlePreViewModel> expected = serviceToTest.getTopArticles(now);
        assertEquals(expected.get(0).getId(), sportArticle.getId());
        assertEquals(expected.get(1).getId(), worldArticle.getId());
        assertEquals(expected.get(2).getId(), entertainmentArticle.getId());
        assertEquals(expected.size(), 3);
    }

    @Test
    void getAllTopArticlesIdsTest() {
        LocalDateTime now = LocalDateTime.now();
        List<String> ids = new ArrayList<>();
        ids.add(entertainmentArticle.getId());
        ids.add(worldArticle.getId());
        ids.add(sportArticle.getId());
        when(mockArticleRepository.findAllByTopIsTrue(now))
               .thenReturn(ids);
        List<String> expected = serviceToTest.getAllTopArticlesIds(now);
        assertEquals(expected.get(0), entertainmentArticle.getId());
        assertEquals(expected.get(1), worldArticle.getId());
        assertEquals(expected.get(2), sportArticle.getId());
    }

    @Test
    void getArticleViewModelByUrlTest() {
        when(mockArticleRepository.findFirstByUrlOrderByCreatedDesc(worldArticle.getUrl()))
                .thenReturn(worldArticle);
        ArticleViewModel expected = serviceToTest.getArticleViewModelByUrl(worldArticle.getUrl());
        assertEquals(expected.getTitle(), worldArticle.getTitle());
        assertEquals(expected.getId(), worldArticle.getId());
    }

    @Test
    void getArticleByIdTest() {
        when(mockArticleRepository.findById(worldArticle.getId()))
                .thenReturn(Optional.of(worldArticle));
        ArticleServiceModel expected = serviceToTest.getArticleById(worldArticle.getId());
        assertEquals(expected.getTitle(), worldArticle.getTitle());
        assertEquals(expected.getId(), worldArticle.getId());
    }

    @Test
    void getArticleUrlByIdTest() {
        when(mockArticleRepository.findById(worldArticle.getId()))
                .thenReturn(Optional.of(worldArticle));
        String expected = serviceToTest.getArticleUrlById(worldArticle.getId());
        assertEquals(expected, worldArticle.getUrl());
    }

    @Test
    void getArticleUrlByCommentIdTest() {
        when(mockArticleRepository.findArticleEntityUrlByCommentId(first.getId()))
                .thenReturn(worldArticle.getUrl());
        String expected = serviceToTest.getArticleUrlByCommentId(first.getId());
        assertEquals(expected, worldArticle.getUrl());
    }

    @Test
    void exportArticlesTest() {
        when(mockArticleRepository.findAll())
                .thenReturn(List.of(sportArticle, worldArticle, entertainmentArticle));
        List<ArticleEntityExportDto> expected = serviceToTest.exportArticles();
        assertEquals(expected.get(0).getTitle(), sportArticle.getTitle());
        assertEquals(expected.get(1).getUrl(), worldArticle.getUrl());
        assertEquals(expected.get(2).getImageUrl(), entertainmentArticle.getImageUrl());
    }

    @Test
    void getArticleByUrlTest() {
        when(mockArticleRepository.findByUrl(worldArticle.getUrl()))
                .thenReturn(Optional.of(worldArticle));
        ArticleServiceModel expected = serviceToTest.getArticleByUrl(worldArticle.getUrl());
        assertEquals(expected.getTitle(), worldArticle.getTitle());
    }

    @Test
    void getTimePeriodsTest() {
        List<String> actual = List.of("Today", "Last three days", "Last week", "Last month", "Last year");
        List<String> expected = serviceToTest.getTimePeriods();
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
        assertEquals(expected.get(3), actual.get(3));
    }

    @Test
    void getArticleStatusesTest() {
        List<String> actual = List.of("Activated", "Waiting");
        List<String> expected = serviceToTest.getArticleStatuses();
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));

    }

    @Test
    void getLocalDateTimeFromStringTest() {
        String timeString = "2022-10-23T21:44";
        LocalDateTime time = serviceToTest.getLocalDateTimeFromString(timeString);
        LocalDateTime timeNull = serviceToTest.getLocalDateTimeFromString(null);
        assertEquals(21, time.getHour());
        assertEquals(44, time.getMinute());
        assertEquals(23, time.getDayOfMonth());
        assertNull(timeNull);
    }

    @Test
    void hasArticlesTest() {
        when(mockArticleRepository.count())
                .thenReturn(3L);
        assertTrue(serviceToTest.hasArticles());
    }

    @Test
    void increaseSeenByOneTest() {
        lenient().doNothing().when(mockArticleRepository).increaseSeen(worldArticle.getId(), 1);
        serviceToTest.increaseSeenByOne(worldArticle.getId(), 0);
        verify(mockArticleRepository, times(1)).increaseSeen(worldArticle.getId(), 1);
    }

    @Test
    void getCategoryViewsTest() {
        when(mockArticleRepository.getTotalArticleViews())
                .thenReturn(66);
        when(mockArticleRepository.getTotalViewsByCategoryNameEnum(isA(CategoryNameEnum.class))).thenReturn(1);
        List<CategoryViewsCountModel> expected = serviceToTest.getCategoryViews();
        assertEquals(expected.get(1).getViews(), 1);
        assertEquals(expected.get(2).getTotalViews(), 66);
    }
}