package project.dailynail.models.dtos;

public class UserFullNameAndEmailDto {
    private String fullName;
    private String email;

    public UserFullNameAndEmailDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public UserFullNameAndEmailDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserFullNameAndEmailDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
