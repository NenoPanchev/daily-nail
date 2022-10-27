package project.dailynail.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.dailynail.services.CloudinaryService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @MockBean
    private CloudinaryService cloudinaryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testIndex() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("topSport"))
                .andExpect(model().attributeExists("sports"))
                .andExpect(model().attributeExists("topEntertainment"))
                .andExpect(model().attributeExists("entertainments"))
                .andExpect(model().attributeExists("topWorld"))
                .andExpect(model().attributeExists("world"))
                .andExpect(model().attributeExists("topCovid"))
                .andExpect(model().attributeExists("covid"))
                .andExpect(model().attributeExists("latestFive"))
                .andExpect(model().attributeExists("latestNine"))
                .andExpect(model().attributeExists("popular"))
                .andExpect(model().attributeExists("topArticles"))
                .andExpect(model().attributeExists("topBusiness"))
                .andExpect(model().attributeExists("business"))
                .andExpect(model().attributeExists("topTechnology"))
                .andExpect(model().attributeExists("technology"))
                .andExpect(view().name("index"));
    }
}

