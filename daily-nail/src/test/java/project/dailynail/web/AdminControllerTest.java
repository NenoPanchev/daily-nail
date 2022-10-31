package project.dailynail.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.entities.UserRoleEntity;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.repositories.*;
import project.dailynail.services.AdminService;
import project.dailynail.services.CloudinaryService;
import project.dailynail.services.impl.AdminServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    private static final String ADMIN_CONTROLLER_PREFIX = "/admin";
    private UserEntity testUser;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CloudinaryService cloudinaryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        UserRoleEntity userRole = userRoleRepository.findByRole(Role.USER).orElseThrow();
        UserRoleEntity adminRole = userRoleRepository.findByRole(Role.ADMIN).orElseThrow();
        testUser = new UserEntity()
                .setEmail("test@user.com")
                .setFullName("User User")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(userRole));
        UserEntity admin = new UserEntity()
                .setEmail("admin@admin.com")
                .setFullName("Admin Admin")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(adminRole, userRole));
        userRepository.save(testUser);
        userRepository.save(admin);
    }

    @AfterEach
    void cleanUp() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAdminPage() throws Exception {
        mockMvc.perform(get(ADMIN_CONTROLLER_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    void testGetAdminPageWithoutBeingAdmin() throws Exception {
        mockMvc.perform(get(ADMIN_CONTROLLER_PREFIX))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testManageAccountsPage() throws Exception {
        mockMvc.perform(get(ADMIN_CONTROLLER_PREFIX + "/accounts"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("accounts-management"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testEditRolePage() throws Exception {
        mockMvc.perform(get(ADMIN_CONTROLLER_PREFIX + "/accounts/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("account-role"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testEditRole() throws Exception {

        mockMvc.perform(post(ADMIN_CONTROLLER_PREFIX + "/accounts/edit")
        .param("email", testUser.getEmail())
        .param("role", "Editor")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());
        UserEntity test = userRepository.findByIdFetch(testUser.getId());
        StringBuilder roles = new StringBuilder();
        test.getRoles().forEach(role -> roles.append(role.getRole().name()));
        Assertions.assertTrue(roles.toString().contains("EDITOR"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testEditRoleInvalidData() throws Exception {

        mockMvc.perform(post(ADMIN_CONTROLLER_PREFIX + "/accounts/edit")
                .param("email", "")
                .param("role", "InvalidRole")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAdminDataPage() throws Exception {
        mockMvc.perform(get(ADMIN_CONTROLLER_PREFIX + "/data"))
                .andExpect(status().isOk())
                .andExpect(view().name("data"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testStatsPage() throws Exception {
        articleRepository.save(new ArticleEntity()
        .setSeen(11));
        mockMvc.perform(get(ADMIN_CONTROLLER_PREFIX + "/stats"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authorized"))
                .andExpect(model().attributeExists("categoryViews"))
                .andExpect(view().name("stats"));
    }

}
