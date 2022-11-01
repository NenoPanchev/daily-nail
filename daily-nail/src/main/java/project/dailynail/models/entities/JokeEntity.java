package project.dailynail.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
@Table(name = "jokes")
public class JokeEntity extends BaseEntity{
    private UserEntity author;
    private String text;
    private LocalDateTime created;

    public JokeEntity() {
    }

    @ManyToOne
    public UserEntity getAuthor() {
        return author;
    }

    public JokeEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    @Column(columnDefinition = "TEXT")
    public String getText() {
        return text;
    }

    public JokeEntity setText(String text) {
        this.text = text;
        return this;
    }

    @PastOrPresent
    @Column
    public LocalDateTime getCreated() {
        return created;
    }

    public JokeEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
}
