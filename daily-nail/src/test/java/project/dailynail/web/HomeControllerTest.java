package project.dailynail.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.repositories.CategoryRepository;
import project.dailynail.services.AdminService;
import project.dailynail.services.ArticleService;
import project.dailynail.services.CloudinaryService;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @MockBean
    private CloudinaryService cloudinaryService;
//    @MockBean
//    private AdminService adminService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleService articleService;

//    @BeforeEach
//    void setUp() throws FileNotFoundException {
//        articleService.seedArticles();
//    }
//
//    @AfterEach
//    void cleanUp() {
//        articleRepository.deleteAll();
//    }

    @Test
    void testIndex() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().is3xxRedirection());
//                .andExpect(model().attributeExists("topSport"))
//                .andExpect(model().attributeExists("sports"))
//                .andExpect(model().attributeExists("topEntertainment"))
//                .andExpect(model().attributeExists("entertainments"))
//                .andExpect(model().attributeExists("topWorld"))
//                .andExpect(model().attributeExists("world"))
//                .andExpect(model().attributeExists("topCovid"))
//                .andExpect(model().attributeExists("covid"))
//                .andExpect(model().attributeExists("latestFive"))
//                .andExpect(model().attributeExists("latestNine"))
//                .andExpect(model().attributeExists("popular"))
//                .andExpect(model().attributeExists("topArticles"))
//                .andExpect(model().attributeExists("topBusiness"))
//                .andExpect(model().attributeExists("business"))
//                .andExpect(model().attributeExists("topTechnology"))
//                .andExpect(model().attributeExists("technology"))
//                .andExpect(view().name("index"));
    }
}

