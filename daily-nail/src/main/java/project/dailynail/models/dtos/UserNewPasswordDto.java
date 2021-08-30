package project.dailynail.models.dtos;

import org.hibernate.validator.constraints.Length;

public class UserNewPasswordDto {
    private String newPassword;

    public UserNewPasswordDto() {
    }

    @Length(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    public String getNewPassword() {
        return newPassword;
    }

    public UserNewPasswordDto setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}
