package project.dailynail.services;

import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.service.UserRoleServiceModel;

import java.util.List;

public interface UserRoleService {
    void seedUserRoles();
    UserRoleServiceModel findByRole(Role role);
    List<UserRoleServiceModel> findAllByRoleIn(Role... roles);
}
