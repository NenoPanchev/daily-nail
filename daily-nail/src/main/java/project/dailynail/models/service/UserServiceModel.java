package project.dailynail.models.service;

import project.dailynail.models.entities.UserRole;

import java.util.List;

public class UserServiceModel extends BaseServiceModel{
    private String email;
    private String fullName;
    private String password;
    private List<UserRole> roles;

    public UserServiceModel() {
    }

    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public UserServiceModel setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }
}
