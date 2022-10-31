package project.dailynail.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.entities.UserRoleEntity;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.repositories.CommentRepository;
import project.dailynail.repositories.UserRepository;
import project.dailynail.repositories.UserRoleRepository;
import project.dailynail.services.AdminService;
import project.dailynail.services.CloudinaryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final String USER_CONTROLLER_PREFIX = "/users";
    private UserEntity testUser;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CloudinaryService cloudinaryService;
    @MockBean
    private AdminService adminService;
    @MockBean
    private PasswordEncoder passwordEncoder;
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
        Mockito.when(passwordEncoder.encode("1234"))
                .thenReturn("1234");
        Mockito.when(passwordEncoder.encode("1111"))
                .thenReturn("1111");
        UserRoleEntity userRole = userRoleRepository.save(new UserRoleEntity().setRole(Role.USER));
        testUser = new UserEntity()
                .setEmail("user@user.bg")
                .setFullName("User User")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(userRole));
        userRepository.save(testUser);
    }

    @AfterEach
    void cleanUp() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get(USER_CONTROLLER_PREFIX + "/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("latestNine"))
                .andExpect(model().attributeExists("popular"))
                .andExpect(view().name("login"));
    }

    @Test
    void testLoginBadCredentials() throws Exception {
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/login-error")
        .param("email", "")
        .param("password", "11")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testRegisterPage() throws Exception {
        mockMvc.perform(get(USER_CONTROLLER_PREFIX + "/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("latestNine"))
                .andExpect(model().attributeExists("popular"))
                .andExpect(view().name("register"));
    }

    @Test
    void testRegister() throws Exception {
        Assertions.assertEquals(1, userRepository.count());
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/register")
                .param("email", "test@user.com")
                .param("fullName", "Test User")
                .param("password", "1234")
                .param("confirmPassword", "1234")
                .param("acceptedTerms", String.valueOf(true))
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(2, userRepository.count());

    }

    @Test
    void testRegisterWithBadCredentials() throws Exception {
        Assertions.assertEquals(1, userRepository.count());
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/register")
                .param("email", "Invalid@Email")
                .param("fullName", "Test User")
                .param("password", "1")
                .param("confirmPassword", "4")
                .param("acceptedTerms", String.valueOf(false))
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, userRepository.count());

    }

    @Test
    void testRegisterExistingUser() throws Exception {
        Assertions.assertEquals(1, userRepository.count());

        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/register")
                .param("email", "user@user.bg")
                .param("fullName", "User User")
                .param("password", "1234")
                .param("confirmPassword", "1234")
                .param("acceptedTerms", String.valueOf(true))
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assertions.assertEquals(1, userRepository.count());

    }

    @Test
    void testTermsAndConditionsPage() throws Exception {
        mockMvc.perform(get(USER_CONTROLLER_PREFIX + "/terms-and-conditions"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("latestNine"))
                .andExpect(model().attributeExists("popular"))
                .andExpect(view().name("terms-and-conditions"));
    }

    @Test
    @WithMockUser(username = "user@user.bg", roles = "USER")
    void testProfileSettingsPage() throws Exception {
        mockMvc.perform(get(USER_CONTROLLER_PREFIX + "/profile/settings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("latestNine"))
                .andExpect(model().attributeExists("principalName"))
                .andExpect(model().attributeExists("principalEmail"))
                .andExpect(view().name("profile-settings"));
    }

    @Test
    @WithMockUser(username = "user@user.bg", roles = "USER")
    void testChangeProfileSettings() throws Exception {
        String newFullName = "New Name";
        String newEmail = "new@email.com";
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/profile/settings")
        .param("fullName", newFullName)
        .param("email", newEmail)
        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        UserEntity test = userRepository.findByIdFetch(testUser.getId());
        assertEquals(newEmail, test.getEmail());
        assertEquals(newFullName, test.getFullName());
    }

    @Test
    @WithMockUser(username = "user@user.bg", roles = "USER")
    void testChangeProfileSettingsWithInvalidData() throws Exception {
        String newFullName = "New Name";
        String newEmail = "new@email.com";
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/profile/settings")
                .param("fullName", newFullName)
                .param("email", "")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        UserEntity test = userRepository.findByIdFetch(testUser.getId());
        assertEquals(testUser.getEmail(), test.getEmail());
        assertEquals(testUser.getFullName(), test.getFullName());
    }

    @Test
    @WithMockUser(username = "user@user.bg", roles = "USER")
    void testChangeProfileSettingsWithSameEmail() throws Exception {
        UserRoleEntity userRole = userRoleRepository.save(new UserRoleEntity().setRole(Role.USER));
        UserEntity testSecondUser = new UserEntity()
                .setEmail("second@user.bg")
                .setFullName("User User")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(userRole));
        userRepository.save(testSecondUser);
        String newFullName = "New Name";
        String newEmail = "new@email.com";
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/profile/settings")
                .param("fullName", newFullName)
                .param("email", testSecondUser.getEmail())
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        UserEntity test = userRepository.findByIdFetch(testUser.getId());
        assertEquals(testUser.getEmail(), test.getEmail());
        assertEquals(testUser.getFullName(), test.getFullName());
    }

    @Test
    @WithMockUser(username = "user@user.bg", roles = "USER")
    void testPasswordChangePage() throws Exception {
        mockMvc.perform(get(USER_CONTROLLER_PREFIX + "/profile/change-password"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("latestNine"))
                .andExpect(model().attributeExists("principalName"))
                .andExpect(model().attributeExists("principalEmail"))
                .andExpect(view().name("change-password"));
    }

    @Test
    @WithMockUser(username = "user@user.bg", roles = "USER")
    void testChangePassword() throws Exception {
        String oldPassword = "1234";
        String newPassword = "1111";
        String confirmNewPassword = "1111";
        Mockito.when(passwordEncoder.matches(oldPassword, "1234"))
                .thenReturn(true);
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/profile/change-password")
                .param("oldPassword", oldPassword)
                .param("newPassword", newPassword)
                .param("confirmNewPassword", confirmNewPassword)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        UserEntity test = userRepository.findByIdFetch(testUser.getId());
        assertEquals(newPassword, test.getPassword());
    }

    @Test
    @WithMockUser(username = "user@user.bg", roles = "USER")
    void testChangePasswordWrong() throws Exception {
        String oldPassword = "wrong";
        String newPassword = "1111";
        String confirmNewPassword = "1111";
        Mockito.when(passwordEncoder.matches(oldPassword, "1234"))
                .thenReturn(false);
        Mockito.when(passwordEncoder.matches("1234", "1234"))
                .thenReturn(true);
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/profile/change-password")
                .param("oldPassword", oldPassword)
                .param("newPassword", newPassword)
                .param("confirmNewPassword", confirmNewPassword)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        UserEntity test = userRepository.findByIdFetch(testUser.getId());
        assertEquals(testUser.getPassword(), test.getPassword());
    }

    @Test
    @WithMockUser(username = "user@user.bg", roles = "USER")
    void testChangePasswordWithInvalidData() throws Exception {
        String oldPassword = "1234";
        String newPassword = "1";
        String confirmNewPassword = "1";
        Mockito.when(passwordEncoder.matches(oldPassword, "1234"))
                .thenReturn(true);
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/profile/change-password")
                .param("oldPassword", oldPassword)
                .param("newPassword", newPassword)
                .param("confirmNewPassword", confirmNewPassword)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        UserEntity test = userRepository.findByIdFetch(testUser.getId());
        assertEquals(testUser.getPassword(), test.getPassword());
    }

    @Test
    @WithMockUser(username = "user@user.bg", roles = "USER")
    void testChangePasswordWithSamePassword() throws Exception {
        String oldPassword = "1234";
        String newPassword = "1234";
        String confirmNewPassword = "1234";
        Mockito.when(passwordEncoder.matches(oldPassword, "1234"))
                .thenReturn(true);
        mockMvc.perform(post(USER_CONTROLLER_PREFIX + "/profile/change-password")
                .param("oldPassword", oldPassword)
                .param("newPassword", newPassword)
                .param("confirmNewPassword", confirmNewPassword)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        UserEntity test = userRepository.findByIdFetch(testUser.getId());
        assertEquals(testUser.getPassword(), test.getPassword());
    }
}