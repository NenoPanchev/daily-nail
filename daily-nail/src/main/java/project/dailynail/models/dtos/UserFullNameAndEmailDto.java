package project.dailynail.models.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserFullNameAndEmailDto {
    private String fullName;
    private String email;

    public UserFullNameAndEmailDto() {
    }

    @Length(max = 30, message = "Name cannot be more than 30 characters")
    public String getFullName() {
        return fullName;
    }

    public UserFullNameAndEmailDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @NotEmpty(message = "Field cannot be empty")
    @Email(message = "Enter valid email address")
    public String getEmail() {
        return email;
    }

    public UserFullNameAndEmailDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
