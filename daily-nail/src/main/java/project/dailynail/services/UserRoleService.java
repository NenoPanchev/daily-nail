package project.dailynail.services;

import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.service.UserRoleServiceModel;

public interface UserRoleService {
    void seedUserRoles();
    UserRoleServiceModel findByRole(Role role);
}
