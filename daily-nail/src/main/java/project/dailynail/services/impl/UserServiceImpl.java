package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.dtos.UserFullNameAndEmailDto;
import project.dailynail.models.dtos.UserNewPasswordDto;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.entities.UserRoleEntity;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.service.UserServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.repositories.UserRepository;
import project.dailynail.services.UserRoleService;
import project.dailynail.services.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final static String DEFAULT_FULL_NAME = "Anonymous";

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final DailyNailUserService dailyNailUserService;
    private final ServiceLayerValidationUtil serviceLayerValidationUtil;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, DailyNailUserService dailyNailUserService, ServiceLayerValidationUtil serviceLayerValidationUtil) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.dailyNailUserService = dailyNailUserService;
        this.serviceLayerValidationUtil = serviceLayerValidationUtil;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {

            UserEntity admin = new UserEntity()
                    .setEmail("admin@admin.bg")
                    .setFullName("Daily Nail")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(userRoleService.findAllByRoleIn(Role.ADMIN, Role.EDITOR, Role.REPORTER, Role.USER)
                        .stream()
                        .map(serviceModel -> modelMapper.map(serviceModel, UserRoleEntity.class))
                        .collect(Collectors.toSet()));

            UserEntity editor = new UserEntity()
                    .setEmail("editor@editor.bg")
                    .setFullName("Editor Editor")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(userRoleService.findAllByRoleIn(Role.EDITOR, Role.USER)
                            .stream()
                            .map(serviceModel -> modelMapper.map(serviceModel, UserRoleEntity.class))
                            .collect(Collectors.toSet()));

            UserEntity reporter = new UserEntity()
                    .setEmail("reporter@reporter.bg")
                    .setFullName("Reporter Reporter")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(userRoleService.findAllByRoleIn(Role.REPORTER, Role.USER)
                            .stream()
                            .map(serviceModel -> modelMapper.map(serviceModel, UserRoleEntity.class))
                            .collect(Collectors.toSet()));

            UserEntity user = new UserEntity()
                    .setEmail("user@user.bg")
                    .setFullName("User User")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(Stream.of(modelMapper.map(userRoleService.findByRole(Role.USER), UserRoleEntity.class))
                        .collect(Collectors.toSet()));

            userRepository.saveAll(List.of(admin, editor, reporter, user));
        }
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(entityOpt -> modelMapper.map(entityOpt, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository
                .existsByEmail(email);
    }

    @Override
    public void registerAndLoginUser(UserServiceModel userServiceModel) {
        serviceLayerValidationUtil.validate(userServiceModel);

        UserEntity newUser = modelMapper.map(userServiceModel, UserEntity.class)
                .setFullName(userServiceModel.getFullName().isBlank() ? DEFAULT_FULL_NAME
                        : userServiceModel.getFullName())
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setRoles(Stream.of(modelMapper.map(userRoleService.findByRole(Role.USER), UserRoleEntity.class))
                        .collect(Collectors.toSet()));

        userRepository.saveAndFlush(newUser);

        loadPrincipal(newUser.getEmail());
    }

    @Override
    public String getUserNameByEmail(String email) {
        String s = userRepository.getFullNameByEmail(email)
                .orElseThrow(ObjectNotFoundException::new);
        return s;
    }

    @Override
    public void loadPrincipal(String email) {

        UserDetails newPrincipal = dailyNailUserService.loadUserByUsername(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                newPrincipal,
                newPrincipal.getPassword(),
                newPrincipal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean passwordMatches(String principalEmail, String oldPassword) {

        return passwordEncoder.matches(
                oldPassword,
                userRepository.getPasswordByEmail(principalEmail).orElseThrow(ObjectNotFoundException::new));
    }

    @Override
    @Transactional
    public void updatePassword(UserNewPasswordDto userNewPasswordDto, String principalEmail) {
        String newPassword = userNewPasswordDto.getNewPassword();
        serviceLayerValidationUtil.validate(newPassword);
        userRepository.updatePasswordByEmail(passwordEncoder.encode(newPassword), principalEmail);
    }

    @Override
    public UserServiceModel getPrincipal() {
        String principalEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserServiceModel principal = findByEmail(principalEmail);
        return principal;
    }

    @Override
    public List<String> getAllAuthorNames() {
        return userRepository.findAllUserFullNamesByWhoHasMoreThanOneRole();
    }

    @Transactional
    public boolean updateFullNameAndEmailIfNeeded(UserFullNameAndEmailDto userFullNameAndEmailDto, String principalEmail) {
        serviceLayerValidationUtil.validate(userFullNameAndEmailDto);

        List<Map<String, String>> principalIdAndFullName = userRepository.getIdAndFullNameByEmail(principalEmail);

        String principalId = principalIdAndFullName.get(0).get("id");
        String principalFullName = principalIdAndFullName.get(0).get("fullName");

        boolean updatedFullName = false;
        boolean updatedEmail = false;

        if (!userFullNameAndEmailDto.getFullName().equals(principalFullName)) {
            userRepository.updateUserFullNameById(userFullNameAndEmailDto.getFullName(), principalId);
            updatedFullName = true;
        }

        if (!userFullNameAndEmailDto.getEmail().equals(principalEmail)) {
            userRepository.updateUserEmailById(userFullNameAndEmailDto.getEmail(), principalId);
            updatedEmail = true;
        }

        return updatedFullName || updatedEmail;
    }
}
