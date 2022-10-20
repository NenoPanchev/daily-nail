package project.dailynail.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.entities.UserRoleEntity;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DailyNailUserServiceTest {
    private UserEntity testUser;
    private UserRoleEntity userRole, editorRole;
    private DailyNailUserService serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        // ARRANGE
        serviceToTest = new DailyNailUserService(mockUserRepository);
        userRole = new UserRoleEntity().setRole(Role.USER);
        editorRole = new UserRoleEntity().setRole(Role.EDITOR);

        testUser = new UserEntity()
                .setFullName("Test Testov")
                .setEmail("test@test.com")
                .setPassword("1234")
                .setRoles(List.of(userRole, editorRole))
                .setArticles(new ArrayList<>());

    }

    @Test
    void testUserFound() {
        // Arrange
        Mockito.when(mockUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        // Act
        UserDetails actual = serviceToTest.loadUserByUsername(testUser.getEmail());

        // Assert
        String expectedRoles = "ROLE_EDITOR, ROLE_USER";
        String actualRoles = actual.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        Assertions.assertEquals(actual.getUsername(), testUser.getEmail());
        Assertions.assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("invalid_user_email@not-exist.com")
        );
    }
}