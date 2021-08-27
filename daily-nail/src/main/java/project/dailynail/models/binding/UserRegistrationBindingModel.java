package project.dailynail.models.binding;

import org.hibernate.validator.constraints.Length;
import project.dailynail.models.validators.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Паролите трябва да съвпадат"
)
public class UserRegistrationBindingModel {
    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;

    public UserRegistrationBindingModel() {
    }

    @NotEmpty(message = "Полето не може да е празно")
    @Email(message = "Въведете валиден имейл адрес")
    public String getEmail() {
        return email;
    }

    public UserRegistrationBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Length(max = 30, message = "Името трябва да бъде не повече от 30 символа")
    public String getFullName() {
        return fullName;
    }

    public UserRegistrationBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Length(min = 4, max = 20, message = "Паролата трябва да бъде между 4 и 20 символа.")
    public String getPassword() {
        return password;
    }

    public UserRegistrationBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @Length(min = 4, max = 20)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
