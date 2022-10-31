package project.dailynail.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import project.dailynail.models.entities.*;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.repositories.*;
import project.dailynail.services.AdminService;
import project.dailynail.services.CloudinaryService;
import project.dailynail.services.impl.ArticleServiceImpl;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {
    private static final String ARTICLE_CONTROLLER_PREFIX = "/articles";
    private ArticleEntity sportArticle, worldArticle, entertainmentArticle;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CloudinaryService cloudinaryService;
    @MockBean
    private AdminService adminService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        UserRoleEntity userRole = userRoleRepository.save(new UserRoleEntity().setRole(Role.USER));
        UserRoleEntity editorRole = userRoleRepository.save(new UserRoleEntity().setRole(Role.EDITOR));
        UserRoleEntity adminRole = userRoleRepository.save(new UserRoleEntity().setRole(Role.ADMIN));
        CategoryEntity sportCategory = categoryRepository.save(new CategoryEntity().setCategoryName(CategoryNameEnum.SPORTS).setSubcategories(new HashSet<>()));
        CategoryEntity worldCategory = categoryRepository.save(new CategoryEntity().setCategoryName(CategoryNameEnum.WORLD).setSubcategories(new HashSet<>()));
        CategoryEntity entertainmentCategory = categoryRepository.save(new CategoryEntity().setCategoryName(CategoryNameEnum.ENTERTAINMENT).setSubcategories(new HashSet<>()));
        // Users
        UserEntity admin = new UserEntity()
                .setEmail("admin@admin.com")
                .setFullName("Admin Admin")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(adminRole, editorRole, userRole));
        userRepository.save(admin);

        UserEntity editor = new UserEntity()
                .setEmail("editor@editor.com")
                .setFullName("Editor Editor")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(editorRole, userRole));
        userRepository.save(editor);
        UserEntity user = new UserEntity()
                .setEmail("user@user.com")
                .setFullName("User User")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(userRole));
        userRepository.save(user);

        // Comments
//        first = (CommentEntity) new CommentEntity()
//            .setText("111")
//                .setTimePosted(LocalDateTime.now())
//                .setLikes(1)
//                .setDislikes(1)
//                .setAuthor(editor)
//                .setArticle(null);
//        second = (CommentEntity) new CommentEntity().setId("2");
//        second.setText("222")
//                .setTimePosted(LocalDateTime.now())
//                .setLikes(1)
//                .setDislikes(1)
//                .setAuthor(user)
//                .setArticle(null);

        // Articles
         sportArticle = new ArticleEntity()
                .setAuthor(editor)
                .setActivated(true)
                .setCategory(sportCategory)
                .setText("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
                        "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                .setComments(new ArrayList<>())
                .setCreated(LocalDateTime.now())
                .setPosted(LocalDateTime.now())
                .setDisabledComments(false)
                .setSubcategory(null)
                .setSeen(11)
                .setImageUrl("sportsUrl")
                .setTitle("Sports Title")
                .setUrl("sports-title")
                .setTop(true);
        articleRepository.save(sportArticle);

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
                .setUrl("world-title")
                .setTop(true);
        articleRepository.save(worldArticle);

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
                .setUrl("entertainment-title")
                .setTop(true);
        articleRepository.save(entertainmentArticle);
    }
    
    @AfterEach
    void cleanUp() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        userRoleRepository.deleteAll();
    }
    
    @Test
    void viewArticlesByCategoryTest() throws Exception {
        mockMvc.perform(get(ARTICLE_CONTROLLER_PREFIX + "/categories/{category}", "world"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("latestNine"))
                .andExpect(model().attributeExists("latestFive"))
                .andExpect(model().attributeExists("popular"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(view().name("articles-by-category"));
    }

    @Test
    void viewArticlesByCategoryPageTest() throws Exception {
        mockMvc.perform(get(ARTICLE_CONTROLLER_PREFIX + "/categories/{category}/", "world")
        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("latestNine"))
                .andExpect(model().attributeExists("latestFive"))
                .andExpect(model().attributeExists("popular"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(view().name("articles-by-category"));
    }

    @Test
    void viewArticlePageTest() throws Exception {
        mockMvc.perform(get(ARTICLE_CONTROLLER_PREFIX + "/a/{id}/", worldArticle.getUrl()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("latestNine"))
                .andExpect(model().attributeExists("latestFive"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("popular"))
                .andExpect(model().attributeExists("principal_name"))
                .andExpect(view().name("article"));
    }

    @Test
    @WithMockUser(roles = "EDITOR")
    void viewEditArticlePageTest() throws Exception {
        mockMvc.perform(get(ARTICLE_CONTROLLER_PREFIX + "/edit/{id}/", worldArticle.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articleEditBindingModel"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("id"))
                .andExpect(view().name("article-edit"));
    }

    @Test
    @WithMockUser(roles = "EDITOR")
    void editArticlePageTest() throws Exception {
        Mockito.when(cloudinaryService.uploadImage(null))
                .thenReturn("imageUrl");
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.getContentType())
                .thenReturn("application/octet-stream");
        mockMvc.perform(post(ARTICLE_CONTROLLER_PREFIX + "/edit/{id}", worldArticle.getId())
        .param("id", worldArticle.getId())
        .param("title", worldArticle.getTitle())
        .param("imageUrl", "")
        .param("imageFile", String.valueOf(mockFile))
        .param("text", worldArticle.getText())
        .param("activated", String.valueOf(true))
        .param("posted", String.valueOf(LocalDateTime.now()))
                .param("categoryName", "WORLD")
                .param("disabledComments", "No")
                .param("top", "NO")
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "EDITOR")
    void testDelete() throws Exception {
        assertEquals(3, articleRepository.count());
        mockMvc.perform(delete(ARTICLE_CONTROLLER_PREFIX + "/delete/{id}", worldArticle.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals(2, articleRepository.count());
    }

    @Test
    @WithMockUser(roles = "EDITOR")
    void viewAllArticlesPageTest() throws Exception {
        mockMvc.perform(get(ARTICLE_CONTROLLER_PREFIX + "/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allArticles"))
                .andExpect(model().attributeExists("authorNames"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("timePeriods"))
                .andExpect(model().attributeExists("articleStatuses"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("page"))
                .andExpect(view().name("all-articles"));
    }

    @Test
    @WithMockUser(roles = "EDITOR")
    void viewAllArticlesPagedTest() throws Exception {
        mockMvc.perform(get(ARTICLE_CONTROLLER_PREFIX + "/all")
        .param("page", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allArticles"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("timePeriods"))
                .andExpect(model().attributeExists("articleStatuses"))
                .andExpect(model().attributeExists("authorNames"))
                .andExpect(view().name("all-articles"));
    }

//    @Test
//    @WithMockUser(roles = "EDITOR")
//    void viewAllArticlesSearchBarTest() throws Exception {
//        mockMvc.perform(get(ARTICLE_CONTROLLER_PREFIX + "/all")
//        .param("authorName", "Editor Editor")
//        .param("category", "Entertainment")
//        .param("timePeriod", "Today")
//        .param("keyWord", "ssssss")
//        .param("articleStatus", "Activated")
//        .param("page", String.valueOf(1)))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("allArticles"))
//                .andExpect(model().attributeExists("authorNames"))
//                .andExpect(model().attributeExists("authorName"))
//                .andExpect(model().attributeExists("categories"))
//                .andExpect(model().attributeExists("category"))
//                .andExpect(model().attributeExists("timePeriods"))
//                .andExpect(model().attributeExists("timePeriod"))
//                .andExpect(model().attributeExists("articleStatuses"))
//                .andExpect(model().attributeExists("articleStatus"))
//                .andExpect(model().attributeExists("keyWord"))
//                .andExpect(model().attributeExists("totalElements"))
//                .andExpect(model().attributeExists("totalPages"))
//                .andExpect(model().attributeExists("page"))
//                .andExpect(view().name("all-articles"));
//    }

    @Test
    @WithMockUser(roles = "EDITOR")
    void viewCreateArticlePageTest() throws Exception {
        mockMvc.perform(get(ARTICLE_CONTROLLER_PREFIX + "/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(view().name("article-create"));
    }

//    @Test
//    @WithMockUser(roles = "EDITOR")
//    void createArticleTest() throws Exception {
//        assertEquals(3, articleRepository.count());
//        Mockito.when(cloudinaryService.uploadImage(null))
//                .thenReturn("imageUrl");
//        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
//        Mockito.when(mockFile.getContentType())
//                .thenReturn("application/octet-stream");
//        mockMvc.perform(post(ARTICLE_CONTROLLER_PREFIX + "/create")
//                .param("title", worldArticle.getTitle() + "22222222222")
//                .param("imageUrl", "https://www.wallpaperup.com/uploads/wallpapers/2014/03/05/286394/ea24b7bd70c8afff182dfac9f24db651.jpg")
//                .param("text", worldArticle.getText())
//                .param("imageFIle", String.valueOf(mockFile))
//                .param("posted", String.valueOf(LocalDateTime.now()))
//                .param("categoryName", "World")
//                .param("disabledComments", "Yes")
//                .param("top", "Yes")
//        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().is3xxRedirection());
//        assertEquals(4, articleRepository.count());
//    }
}