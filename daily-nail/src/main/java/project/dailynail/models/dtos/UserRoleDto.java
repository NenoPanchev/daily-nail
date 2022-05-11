package project.dailynail.models.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRoleDto {
    private String email;
    private String role;

    public UserRoleDto() {
    }

    @NotEmpty
    public String getEmail() {
        return email;
    }

    public UserRoleDto setEmail(String email) {
        this.email = email;
        return this;
    }

    @Pattern(regexp = "^(?!Select Role$).*$", message = "You must select a role")
    public String getRole() {
        return role;
    }

    public UserRoleDto setRole(String role) {
        this.role = role;
        return this;
    }
}
