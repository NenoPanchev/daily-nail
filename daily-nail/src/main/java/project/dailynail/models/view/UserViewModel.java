package project.dailynail.models.view;

public class UserViewModel {
    private String id;
    private String fullName;
    private String email;
    private String role;

    public UserViewModel() {
    }

    public String getFullName() {
        return fullName;
    }

    public UserViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserViewModel setRole(String role) {
        this.role = role;
        return this;
    }

    public String getId() {
        return id;
    }

    public UserViewModel setId(String id) {
        this.id = id;
        return this;
    }
}
