package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import project.dailynail.constants.GlobalConstants;
import project.dailynail.models.dtos.UserFullNameAndEmailDto;
import project.dailynail.models.dtos.UserNewPasswordDto;
import project.dailynail.models.dtos.UserRoleDto;
import project.dailynail.models.dtos.json.UserEntityExportDto;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.entities.UserRoleEntity;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.service.UserRoleServiceModel;
import project.dailynail.models.service.UserServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.models.view.UserViewModel;
import project.dailynail.repositories.UserRepository;
import project.dailynail.services.UserRoleService;
import project.dailynail.services.UserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService serviceToTest;
    private UserEntity editor, user;
    private UserRoleEntity editorRole, userRole;

    @Mock
    UserRepository mockUserRepository;
    @Mock
    private UserRoleService mockUserRoleService;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private DailyNailUserService mockDailyNailUserService;
    @Mock
    private ServiceLayerValidationUtil mockServiceLayerValidationUtil;
    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        editorRole = new UserRoleEntity().setRole(Role.EDITOR);
        userRole = new UserRoleEntity().setRole(Role.USER);
        editor = new UserEntity()
                .setEmail("editor@editor.com")
                .setFullName("Editor Editor")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(editorRole, userRole));
        editor.setId("1");
        user = new UserEntity()
                .setEmail("user@user.com")
                .setFullName("User User")
                .setPassword("1234")
                .setArticles(new ArrayList<>())
                .setRoles(List.of(userRole));
        user.setId("2");

        serviceToTest = new UserServiceImpl(mockUserRepository, mockUserRoleService, mockPasswordEncoder, new ModelMapper(),
                mockDailyNailUserService, mockServiceLayerValidationUtil, new Gson());
    }
    


//    @Test
//    void seedUsersTest() throws FileNotFoundException {
//        UserRoleServiceModel editorRoleServiceModel = new UserRoleServiceModel().setRole(Role.EDITOR);
//        UserRoleServiceModel userRoleServiceModel = new UserRoleServiceModel().setRole(Role.USER);
//        List<UserEntity> entities = List.of(editor, user);
//        when(mockUserRepository.save(editor))
//                .thenReturn(editor);
//        lenient().when(mockUserRepository.saveAll(entities))
//                .thenReturn(entities);
//        when(mockPasswordEncoder.encode("1234"))
//                .thenReturn("1234");
//        lenient().when(mockUserRoleService.findAllByRoleIn(Role.EDITOR, Role.USER))
//                .thenReturn( List.of(editorRoleServiceModel, userRoleServiceModel));
//        lenient().when(mockUserRoleService.findAllByRoleIn(Role.USER))
//                .thenReturn(List.of(userRoleServiceModel));
//        lenient().when(mockModelMapper.map(userRoleServiceModel, UserRoleEntity.class))
//                .thenReturn(userRole);
//        lenient().when(mockModelMapper.map(editorRoleServiceModel, UserRoleEntity.class))
//                .thenReturn(editorRole);
//        doReturn(List.of(userRoleServiceModel)).when(mockUserRoleService).findAllByRoleIn(Role.USER);
//        doReturn(List.of(editorRoleServiceModel, userRoleServiceModel)).when(mockUserRoleService).findAllByRoleIn(Role.EDITOR, Role.USER);
//        serviceToTest.seedUsers();
//        verify(mockUserRepository, times(1)).saveAll(entities);
//    }

    @Test
    void findByEmailTest() {
        when(mockUserRepository.findByEmail(editor.getEmail()))
                .thenReturn(Optional.of(editor));
        UserServiceModel expected = serviceToTest.findByEmail(editor.getEmail());
        assertEquals(expected.getFullName(), editor.getFullName());
    }

    @Test
    void existByEmailTest() {
        when(mockUserRepository.existsByEmail(editor.getEmail()))
                .thenReturn(true);
        assertTrue(serviceToTest.existsByEmail(editor.getEmail()));
    }

    @Test
    void getUserNameByEmailTest() {
        when(mockUserRepository.getFullNameByEmail(editor.getEmail()))
                .thenReturn(Optional.of(editor.getFullName()));
        String expected = serviceToTest.getUserNameByEmail(editor.getEmail());
        assertEquals(expected, editor.getFullName());
    }

    @Test
    void loadPrincipalTest() {
        List<GrantedAuthority> authorities =
                editor.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                        .collect(Collectors.toList());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                editor.getEmail(),
                editor.getPassword(),
                authorities);
        lenient().when(mockDailyNailUserService.loadUserByUsername(editor.getEmail()))
                .thenReturn(userDetails);

    }

    @Test
    void passwordMatchesTest() {
        when(mockUserRepository.getPasswordByEmail(editor.getEmail()))
                .thenReturn(Optional.of(editor.getPassword()));
        when(mockPasswordEncoder.matches(editor.getPassword(), editor.getPassword()))
                .thenReturn(true);
        assertTrue(serviceToTest.passwordMatches(editor.getEmail(), editor.getPassword()));
    }

    @Test
    void updatePasswordTest() {
        UserNewPasswordDto dto = new UserNewPasswordDto().setNewPassword("12345");
        when(mockPasswordEncoder.encode(dto.getNewPassword()))
                .thenReturn(dto.getNewPassword());
        serviceToTest.updatePassword(dto, editor.getEmail());
        verify(mockServiceLayerValidationUtil, times(1)).validate(dto.getNewPassword());
        verify(mockUserRepository, times(1)).updatePasswordByEmail(dto.getNewPassword(), editor.getEmail());
    }

    @Test
    @WithMockUser(username = "editor@editor.com", authorities = {"EDITOR", "USER"})
    void getPrincipalTest() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication())
                .thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName())
                .thenReturn(editor.getEmail());
        when(mockUserRepository.findByEmail(editor.getEmail()))
                .thenReturn(Optional.of(editor));
        UserServiceModel expected = serviceToTest.getPrincipal();
        assertEquals(expected.getFullName(), editor.getFullName());
    }

    @Test
    void getAllAuthorNamesTest() {
        when(mockUserRepository.findAllUserFullNamesByWhoHasMoreThanOneRole())
                .thenReturn(List.of(editor.getFullName()));
        String expected = serviceToTest.getAllAuthorNames().get(0);
        assertEquals(expected, editor.getFullName());
    }

    @Test
    void getAllUsersOrderedByRolesTest() {
        when(mockUserRepository.findAllUsersOrderByRolesDesc())
                .thenReturn(List.of(editor, user));
        List<UserViewModel> expected = serviceToTest.getAllUsersOrderedByRoles();
        assertEquals(expected.get(0).getFullName(), editor.getFullName());
        assertEquals(expected.get(1).getFullName(), user.getFullName());
    }

    @Test
    void getUserViewModelByIdTest() {
        when(mockUserRepository.findById("1"))
                .thenReturn(Optional.of(editor));
        UserViewModel expected = serviceToTest.getUserViewModelById("1");
        assertEquals(expected.getFullName(), editor.getFullName());
    }

    @Test
    void updateRoleTest() {
        lenient().doNothing().when(mockServiceLayerValidationUtil).validate(isA(UserRoleDto.class));
        UserRoleDto userRoleDto = new UserRoleDto()
                .setEmail(editor.getEmail())
                .setRole("EDITOR");
        when(mockUserRepository.findByEmail(editor.getEmail()))
                .thenReturn(Optional.of(editor));
        when(mockUserRepository.saveAndFlush(editor))
                .thenReturn(editor);
        UserEntity expected = mockUserRepository.saveAndFlush(editor);
        serviceToTest.updateRole(userRoleDto);
        verify(mockServiceLayerValidationUtil, times(1)).validate(userRoleDto);
        verify(mockUserRepository, times(1)).saveAndFlush(editor);
        assertEquals(expected.getFullName(), editor.getFullName());
    }

    @Test
    void updateFullNameAndEmailIfNeededTest() {
        UserFullNameAndEmailDto dto = new UserFullNameAndEmailDto()
                .setEmail(editor.getEmail())
                .setFullName(editor.getFullName());
        UserFullNameAndEmailDto newDto = new UserFullNameAndEmailDto()
                .setEmail("asd@adsadas.bg")
                .setFullName("dsdasda defed");
        lenient().doNothing().when(mockServiceLayerValidationUtil).validate(isA(UserFullNameAndEmailDto.class));
        List<Map<String, String>> principalIdAndFullName = List.of(Map.of("id", "1", "fullName", "Editor Editor"));
        when(mockUserRepository.getIdAndFullNameByEmail(editor.getEmail()))
                .thenReturn(principalIdAndFullName);
        lenient().doNothing().when(mockUserRepository).updateUserEmailById(isA(String.class), isA(String.class));
        lenient().doNothing().when(mockUserRepository).updateUserFullNameById(isA(String.class), isA(String.class));
        assertTrue(serviceToTest.updateFullNameAndEmailIfNeeded(newDto, editor.getEmail()));
        assertFalse(serviceToTest.updateFullNameAndEmailIfNeeded(dto, editor.getEmail()));
        verify(mockServiceLayerValidationUtil, times(1)).validate(dto);
    }

    @Test
    void exportUsersTest() {
        when(mockUserRepository.findAllUsersExceptInitials())
                .thenReturn(new ArrayList<>());
        assertEquals(0, serviceToTest.exportUsers().size());
    }

    @Test
    void seedNonInitialUsers() throws FileNotFoundException {
        List<UserEntity> entities = new ArrayList<>();
        when(mockUserRepository.saveAllAndFlush(entities))
                .thenReturn(entities);
        assertEquals(0, mockUserRepository.saveAllAndFlush(entities).size());

    }


//    @Test
//    void registerAndLoginUserTest() {
//        UserServiceImpl mockServiceToTest = (UserServiceImpl) Mockito.spy(serviceToTest);
//
//        UserServiceModel serviceModel = new UserServiceModel()
//                .setEmail(editor.getEmail())
//                .setPassword("1234")
//                .setArticles(new ArrayList<>())
//                .setRoles(new ArrayList<>())
//                .setFullName(editor.getFullName());
//        List<GrantedAuthority> authorities =
//                editor.getRoles()
//                        .stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
//                        .collect(Collectors.toList());
//        lenient().doNothing().when(mockServiceLayerValidationUtil).validate(isA(UserServiceModel.class));
////        lenient().doNothing().when(mockModelMapper).map(isA(UserServiceModel.class), isA(UserEntity.class));
//        lenient().when(mockModelMapper.map(serviceModel, UserEntity.class))
//                .thenReturn(new UserEntity());
////        lenient().doNothing().when(mockDailyNailUserService).loadUserByUsername(isA(String.class));
//        lenient().doNothing().when(mockServiceToTest).loadPrincipal(isA(String.class));
////        lenient().when(mockDailyNailUserService.loadUserByUsername(editor.getEmail()))
////                .thenReturn(new org.springframework.security.core.userdetails.User(
////                        editor.getEmail(),
////                        editor.getPassword(),
////                        authorities
////                ));
//        mockServiceToTest.registerAndLoginUser(serviceModel);
//        verify(mockServiceLayerValidationUtil, times(1)).validate(serviceModel);
//    }
}