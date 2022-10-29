package project.dailynail.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.dailynail.services.CloudinaryService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StaticWebContentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CloudinaryService cloudinaryService;


    @Test
    void testGetCSSFile() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/css/{file}", "style.css"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetCSSImagesFile() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/css/images/{file}", "socials.png"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetImgBootstrapFile() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/img/bootstrap-icons-1.1.0/{file}", "randomfile"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testGetJsFile() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/js/{file}", "custom.js"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetFontsFileOtf() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/fonts/{file}", "FontAwesome.otf"))
                .andExpect(status().isOk()).andReturn();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/fonts/{file}", "fontawesome-webfont.eot"))
                .andExpect(status().isOk()).andReturn();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/fonts/{file}", "fontawesome-webfont.svg"))
                .andExpect(status().isOk()).andReturn();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/fonts/{file}", "fontawesome-webfont.ttf"))
                .andExpect(status().isOk()).andReturn();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/fonts/{file}", "fontawesome-webfont.woff"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetFontsFileEot() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/fonts/{file}", "fontawesome-webfont.eot"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetFontsFileSVG() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/fonts/{file}", "fontawesome-webfont.svg"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetFontsFileTTF() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/fonts/{file}", "fontawesome-webfont.ttf"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetFontsFileWoff() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/fonts/{file}", "fontawesome-webfont.woff"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetImagesFile() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/images/{file}", "403.jpg"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetImagesWeatherIconsFile() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/images/weather-icons/{file}", "01d.png"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetImgFile() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/img/{file}", "logo.jpg"))
                .andExpect(status().isOk()).andReturn();
    }

//    @Test
//    void testGetJsonsFile() throws Exception {
//        mockMvc
//                .perform(MockMvcRequestBuilders.get("/jsons/{file}", "stats.json"))
//                .andExpect(status().isOk()).andReturn();
//    }
}