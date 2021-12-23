package project.dailynail.models.service;

import project.dailynail.models.entities.enums.Role;


public class UserRoleServiceModel extends BaseServiceModel{
    private Role role;

    public UserRoleServiceModel() {
    }

    public Role getRole() {
        return role;
    }

    public UserRoleServiceModel setRole(Role role) {
        this.role = role;
        return this;
    }
}
