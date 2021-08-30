package project.dailynail.models.service;

import org.hibernate.validator.constraints.Length;
import project.dailynail.models.entities.UserRoleEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserServiceModel extends BaseServiceModel{
    private String email;
    private String fullName;
    private String password;
    private List<UserRoleEntity> roles;

    public UserServiceModel() {
    }

    @NotEmpty(message = "Field cannot be empty")
    @Email(message = "Enter valid email address")
    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Length(max = 30, message = "Name cannot be more than 30 characters")
    public String getFullName() {
        return fullName;
    }

    public UserServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Length(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserServiceModel setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }
}
