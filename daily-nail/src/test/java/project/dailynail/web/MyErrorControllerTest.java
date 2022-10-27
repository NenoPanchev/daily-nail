package project.dailynail.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.dailynail.services.CloudinaryService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MyErrorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CloudinaryService cloudinaryService;

    @Test
    void testError() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/error"))
                .andExpect(status().isOk())
                .andExpect((model().attributeExists("popular")))
                .andExpect(view().name("error"));
    }

    @Test
    void test403() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/access-denied"))
                .andExpect(status().isOk())
                .andExpect((model().attributeExists("popular")))
                .andExpect(view().name("403"));
    }

    @Test
    void test404() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/404"))
                .andExpect(status().isOk())
                .andExpect((model().attributeExists("popular")))
                .andExpect(view().name("404"));
    }

    @Test
    void testThrow() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/throw"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testThrowError() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/throw-err"))
                .andExpect(status().is3xxRedirection());
    }

}