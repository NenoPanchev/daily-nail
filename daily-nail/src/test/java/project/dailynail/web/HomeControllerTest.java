package project.dailynail.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.dailynail.DailyNailApplication;
import project.dailynail.config.ApplicationConfig;
import project.dailynail.config.CloudConfig;
import project.dailynail.init.DailyNailAppInit;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.services.CloudinaryService;
import project.dailynail.services.impl.CloudinaryServiceImpl;


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {DailyNailApplication.class, ApplicationConfig.class})
@TestPropertySource(value = {"classpath:application.yml"})
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteAll();
    }

    @Test
    void testIndex() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }
}
