package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.entities.UserRole;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.service.UserRoleServiceModel;
import project.dailynail.repositories.UserRoleRepository;
import project.dailynail.services.UserRoleService;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUserRoles() {
        if (userRoleRepository.count() == 0) {
            UserRole admin = new UserRole().setRole(Role.ADMIN);
            UserRole editor = new UserRole().setRole(Role.EDITOR);
            UserRole reporter = new UserRole().setRole(Role.REPORTER);
            UserRole user = new UserRole().setRole(Role.USER);

            this.userRoleRepository.saveAll(List.of(admin, editor, reporter, user));
        }
    }

    @Override
    public UserRoleServiceModel findByRole(Role role) {
        return userRoleRepository.findByRole(role)
                .map(userRole -> modelMapper.map(userRole, UserRoleServiceModel.class))
                .orElseThrow(ObjectNotFoundException::new);
    }


}
