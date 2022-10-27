package project.dailynail.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.dailynail.services.CloudinaryService;

@SpringBootTest
@AutoConfigureMockMvc
class MaintenanceControllerTest {

    @MockBean
    private CloudinaryService cloudinaryService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testMaintenance() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/maintenance"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((MockMvcResultMatchers.model().attributeExists("popular")))
                .andExpect(MockMvcResultMatchers.view().name("maintenance"));
    }
}