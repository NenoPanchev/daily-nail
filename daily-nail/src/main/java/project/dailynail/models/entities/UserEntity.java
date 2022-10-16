package project.dailynail.models.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NamedEntityGraph(
        name = "user-full",
        attributeNodes = {
                @NamedAttributeNode("roles"),
                @NamedAttributeNode("articles"),
        }
)
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    private String email;
    private String fullName;
    private String password;
    private List<UserRoleEntity> roles;
    private List<ArticleEntity> articles;

    public UserEntity() {
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(name = "full_name", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public UserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Column(nullable = false)
    @Length(min = 4)
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    public List<ArticleEntity> getArticles() {
        return articles;
    }

    public UserEntity setArticles(List<ArticleEntity> articles) {
        this.articles = articles;
        return this;
    }
}
