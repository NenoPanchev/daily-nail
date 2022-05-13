package project.dailynail.models.dtos.json;

import com.google.gson.annotations.Expose;

public class UserEntityExportDto {
    @Expose
    private String email;
    @Expose
    private String fullName;
    @Expose
    private String password;
    @Expose
    private String role;

    public UserEntityExportDto() {
    }

    public String getEmail() {
        return email;
    }

    public UserEntityExportDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserEntityExportDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntityExportDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserEntityExportDto setRole(String role) {
        this.role = role;
        return this;
    }
}
