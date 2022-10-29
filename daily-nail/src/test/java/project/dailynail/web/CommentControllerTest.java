package project.dailynail.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.models.entities.CommentEntity;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.repositories.CommentRepository;
import project.dailynail.services.AdminService;
import project.dailynail.services.CategoryService;
import project.dailynail.services.CloudinaryService;
import project.dailynail.services.UserService;
import project.dailynail.services.impl.TopArticlesServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    private static final String COMMENTS_CONTROLLER_PREFIX = "/comments";
    private CommentEntity first;
    private ArticleEntity sportArticle;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @MockBean
    private CloudinaryService cloudinaryService;
    @MockBean
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        sportArticle = new ArticleEntity()
                .setAuthor(null)
                .setActivated(true)
                .setCategory(null)
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
                .setTop(true);
        articleRepository.save(sportArticle);
        first = new CommentEntity()
                .setText("1111")
                .setTimePosted(LocalDateTime.now())
                .setLikes(1)
                .setDislikes(1)
                .setAuthor(null)
                .setArticle(null);
    }

    @AfterEach
    void cleanUp() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void testAddValidComment() throws Exception {
        mockMvc.perform(post(COMMENTS_CONTROLLER_PREFIX + "/add/{id}", sportArticle.getId())
        .param("text", "1111")
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, commentRepository.count());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testAddInvalidComment() throws Exception {
        String id = sportArticle.getId();
        mockMvc.perform(post(COMMENTS_CONTROLLER_PREFIX + "/add/{id}", id)
                .param("text", "11")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(0, commentRepository.count());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteComment() throws Exception {
        commentRepository.save(first);
        Assertions.assertEquals(1, commentRepository.count());
        String commentId = first.getId();


        mockMvc.perform(get(COMMENTS_CONTROLLER_PREFIX + "/delete/{id}", commentId)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(0, commentRepository.count());
    }
}
