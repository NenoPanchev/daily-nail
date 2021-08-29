package project.dailynail.models.service;

import org.hibernate.validator.constraints.Length;
import project.dailynail.models.entities.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserServiceModel extends BaseServiceModel{
    private String email;
    private String fullName;
    private String password;
    private List<UserRole> roles;

    public UserServiceModel() {
    }

    @NotEmpty(message = "Полето не може да е празно")
    @Email(message = "Въведете валиден имейл адрес")
    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Length(max = 30, message = "Името трябва да бъде не повече от 30 символа")
    public String getFullName() {
        return fullName;
    }

    public UserServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Length(min = 4, max = 20, message = "Паролата трябва да бъде между 4 и 20 символа.")
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
