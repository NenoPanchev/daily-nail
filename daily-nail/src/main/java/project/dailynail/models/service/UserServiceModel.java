package project.dailynail.models.service;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel{
    private String id;
    private String email;
    private String fullName;
    private String password;
    private List<UserRoleServiceModel> roles;
    private List<ArticleServiceModel> articles;

    public UserServiceModel() {
    }

    @NotEmpty(message = "Field cannot be empty")
    @Email(message = "Enter valid email address")
    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Length(max = 30, message = "Name cannot be more than 30 characters")
    public String getFullName() {
        return fullName;
    }

    public UserServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Length(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }


    @Override
    public String toString() {
        return "UserServiceModel{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



    public List<ArticleServiceModel> getArticles() {
        return articles;
    }

    public UserServiceModel setArticles(List<ArticleServiceModel> articles) {
        this.articles = articles;
        return this;
    }

    public List<UserRoleServiceModel> getRoles() {
        return roles;
    }

    public UserServiceModel setRoles(List<UserRoleServiceModel> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public UserServiceModel setId(String id) {
        this.id = id;
        return this;
    }
}
