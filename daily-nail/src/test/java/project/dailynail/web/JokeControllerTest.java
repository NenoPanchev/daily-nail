package project.dailynail.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.dailynail.models.entities.JokeEntity;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.entities.UserRoleEntity;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.repositories.*;
import project.dailynail.services.AdminService;
import project.dailynail.services.CloudinaryService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JokeControllerTest {
    private static final String JOKES_CONTROLLER_PREFIX = "/jokes";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JokeRepository jokeRepository;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private AdminService adminService;
    @MockBean
    private CloudinaryService cloudinaryService;

    @AfterEach
    void cleanUp() {
        jokeRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "EDITOR")
    void getCreateJokePageTest() throws Exception {
        mockMvc.perform(get(JOKES_CONTROLLER_PREFIX + "/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("joke-create"));
    }

    @Test
    @WithMockUser(username = "test@author.com", roles = {"EDITOR", "USER"})
    void createJokeTest() throws Exception {
        assertEquals(0, jokeRepository.count());
        mockMvc.perform(post(JOKES_CONTROLLER_PREFIX + "/create")
        .param("text", "jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj")
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals(1, jokeRepository.count());
    }

    @Test
    @WithMockUser(username = "test@author.com", roles = {"EDITOR", "USER"})
    void createJokeTestWithInvalidData() throws Exception {
        assertEquals(0, jokeRepository.count());
        mockMvc.perform(post(JOKES_CONTROLLER_PREFIX + "/create")
                .param("text", "1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals(0, jokeRepository.count());
    }
}