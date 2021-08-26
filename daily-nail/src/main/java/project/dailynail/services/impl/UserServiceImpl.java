package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final DailyNailUserService dailyNailUserService;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, DailyNailUserService dailyNailUserService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.dailyNailUserService = dailyNailUserService;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {

            User admin = new User()
                    .setEmail("admin@admin.bg")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(List.of(modelMapper.map(userRoleService.findByRole(Role.ADMIN), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.EDITOR), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.REPORTER), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.USER), UserRole.class)));

            User editor = new User()
                    .setEmail("editor@editor.bg")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(List.of(modelMapper.map(userRoleService.findByRole(Role.EDITOR), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.USER), UserRole.class)));

            User reporter = new User()
                    .setEmail("reporter@reporter.bg")
                    .setPassword(passwordEncoder.encode("1234"))
                    .setRoles(List.of(modelMapper.map(userRoleService.findByRole(Role.REPORTER), UserRole.class),
                            modelMapper.map(userRoleService.findByRole(Role.USER), UserRole.class)));

            User user = new User()
                    .setEmail("user@user.bg")
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

}
