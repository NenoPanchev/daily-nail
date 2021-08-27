package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.entities.User;
import project.dailynail.models.entities.UserRole;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.service.UserServiceModel;
import project.dailynail.repositories.UserRepository;
import project.dailynail.services.UserRoleService;
import project.dailynail.services.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final DailyNailUserService dailyNailUserService;
    private final Validator validator;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, DailyNailUserService dailyNailUserService, Validator validator) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.dailyNailUserService = dailyNailUserService;
        this.validator = validator;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {

            User admin = new User()
                    .setEmail("admin@admin.bg")
                    .setFullName("Admin Admin")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(List.of(modelMapper.map(userRoleService.findByRole(Role.ADMIN), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.EDITOR), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.REPORTER), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.USER), UserRole.class)));

            User editor = new User()
                    .setEmail("editor@editor.bg")
                    .setFullName("Editor Editor")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(List.of(modelMapper.map(userRoleService.findByRole(Role.EDITOR), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.USER), UserRole.class)));

            User reporter = new User()
                    .setEmail("reporter@reporter.bg")
                    .setFullName("Reporter Reporter")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(List.of(modelMapper.map(userRoleService.findByRole(Role.REPORTER), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.USER), UserRole.class)));

            User user = new User()
                    .setEmail("user@user.bg")
                    .setFullName("User User")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(List.of(modelMapper.map(userRoleService.findByRole(Role.USER), UserRole.class)));

            userRepository.saveAll(List.of(admin, editor, reporter, user));
        }
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(entityOpt -> modelMapper.map(entityOpt, UserServiceModel.class))
                .orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository
                .existsByEmail(email);
    }

    @Override
    public void registerAndLoginUser(UserServiceModel userServiceModel) {

        Set<ConstraintViolation<UserServiceModel>> violations = validator.validate(userServiceModel);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(sb::append);

            throw new ConstraintViolationException("Error occured: " + sb.toString(), violations);
        }


        User newUser = modelMapper.map(userServiceModel, User.class)
                .setFullName(userServiceModel.getFullName().isBlank() ? "Анонимен"
                        : userServiceModel.getFullName())
                .setRoles(List.of(modelMapper.map(userRoleService.findByRole(Role.USER), UserRole.class)));

        userRepository.saveAndFlush(newUser);

        UserDetails principal = dailyNailUserService.loadUserByUsername(newUser.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
