package project.dailynail.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.entities.UserRoleEntity;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.service.UserRoleServiceModel;
import project.dailynail.repositories.UserRoleRepository;
import project.dailynail.services.UserRoleService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {
    private UserRoleService serviceToTest;
    private UserRoleEntity admin, editor, reporter, user;

    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @BeforeEach
    void setUp() {
        admin = new UserRoleEntity().setRole(Role.ADMIN);
        editor = new UserRoleEntity().setRole(Role.EDITOR);
        reporter = new UserRoleEntity().setRole(Role.REPORTER);
        user = new UserRoleEntity().setRole(Role.USER);

        serviceToTest = new UserRoleServiceImpl(mockUserRoleRepository, new ModelMapper());

    }

    @Test
    void seedUserRolesTest() {
        when(mockUserRoleRepository.count())
                .thenReturn(4L);
        when(mockUserRoleRepository.saveAll(List.of(admin, editor, reporter, user)))
                .thenReturn(List.of(admin, editor, reporter, user));
        serviceToTest.seedUserRoles();
        List<UserRoleEntity> expected = List.of(admin, editor, reporter, user);
        Long expectedCount = mockUserRoleRepository.count();
        Assertions.assertEquals(expectedCount, 4L);
        assertEquals(expected, mockUserRoleRepository.saveAll(expected));
        verify(mockUserRoleRepository, times(1)).saveAll(List.of(admin, editor, reporter, user));
    }

    @Test
    void findByRoleTest() {
        when(mockUserRoleRepository.findByRole(Role.ADMIN))
                .thenReturn(Optional.of(admin));

        UserRoleServiceModel userRoleServiceModel = serviceToTest.findByRole(Role.ADMIN);

        assertEquals(admin.getRole(), userRoleServiceModel.getRole());
    }

    @Test
    void findByRoleThrow() {
        assertThrows(ObjectNotFoundException.class,
                () -> serviceToTest.findByRole(Role.USER));
    }

    @Test
    void findAllByRoleInTest() {
        Role[] roles = new  Role[]{Role.EDITOR, Role.USER};
        when(mockUserRoleRepository.findAllByRoleIn(roles))
                .thenReturn(List.of(editor, user));

        List<UserRoleServiceModel> actual = serviceToTest.findAllByRoleIn(roles);

        assertEquals(actual.stream().count(), 2L);
        assertEquals(editor.getRole(), actual.get(0).getRole());
        assertEquals(user.getRole(), actual.get(1).getRole());
    }
}