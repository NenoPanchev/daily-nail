package project.dailynail.models.binding;

import org.hibernate.validator.constraints.Length;
import project.dailynail.models.validators.FieldMatch;

@FieldMatch(
        first = "newPassword",
        second = "confirmNewPassword",
        message = "Новата и повторената парола трябва да съвпадат"
)
public class UserUpdatePasswordBindingModel {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public UserUpdatePasswordBindingModel() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public UserUpdatePasswordBindingModel setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    @Length(min = 4, max = 20, message = "Паролата трябва да бъде между 4 и 20 символа.")
    public String getNewPassword() {
        return newPassword;
    }

    public UserUpdatePasswordBindingModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    @Length(min = 4, max = 20)
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public UserUpdatePasswordBindingModel setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}
