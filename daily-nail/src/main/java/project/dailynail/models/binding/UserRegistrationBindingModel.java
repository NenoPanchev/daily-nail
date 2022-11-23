package project.dailynail.models.binding;

import org.hibernate.validator.constraints.Length;
import project.dailynail.models.validators.FieldMatch;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords must match"
)
public class UserRegistrationBindingModel {
    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;
    private boolean acceptedTerms;

    public UserRegistrationBindingModel() {
    }

    @NotEmpty(message = "Field cannot be empty")
    @Email(message = "Enter valid email address")
    public String getEmail() {
        return email;
    }

    public UserRegistrationBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Length(max = 30, message = "Name cannot be more than 30 characters")
    public String getFullName() {
        return fullName;
    }

    public UserRegistrationBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Length(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
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

    @AssertTrue(message = "You must accept the terms and conditions in order to make a registration")
    public boolean isAcceptedTerms() {
        return acceptedTerms;
    }

    public UserRegistrationBindingModel setAcceptedTerms(boolean acceptedTerms) {
        this.acceptedTerms = acceptedTerms;
        return this;
    }
}
