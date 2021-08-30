package project.dailynail.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserUpdateNameAndEmailBindingModel {
    private String fullName;
    private String email;

    public UserUpdateNameAndEmailBindingModel() {
    }

    @Length(max = 30, message = "Name cannot be more than 30 characters")
    public String getFullName() {
        return fullName;
    }

    public UserUpdateNameAndEmailBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @NotEmpty(message = "Field cannot be empty")
    @Email(message = "Enter valid email address")
    public String getEmail() {
        return email;
    }

    public UserUpdateNameAndEmailBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
