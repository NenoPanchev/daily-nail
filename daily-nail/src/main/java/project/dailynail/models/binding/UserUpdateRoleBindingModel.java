package project.dailynail.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserUpdateRoleBindingModel {
    private String email;
    private String role;

    public UserUpdateRoleBindingModel() {
    }

    @NotEmpty
    public String getEmail() {
        return email;
    }

    public UserUpdateRoleBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }
    @Pattern(regexp = "^(?!Select Role$).*$", message = "You must select a role")
    public String getRole() {
        return role;
    }

    public UserUpdateRoleBindingModel setRole(String role) {
        this.role = role;
        return this;
    }
}
