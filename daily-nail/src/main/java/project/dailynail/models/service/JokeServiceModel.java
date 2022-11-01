package project.dailynail.models.service;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class JokeServiceModel extends BaseServiceModel{
    private UserServiceModel author;
    private String text;
    private LocalDateTime created;

    public JokeServiceModel() {
    }

    public UserServiceModel getAuthor() {
        return author;
    }

    public JokeServiceModel setAuthor(UserServiceModel author) {
        this.author = author;
        return this;
    }

    @Size(min = 15, max = 1000, message = "Text must be between 15 and 1000 characters")
    public String getText() {
        return text;
    }

    public JokeServiceModel setText(String text) {
        this.text = text;
        return this;
    }

    @PastOrPresent
    public LocalDateTime getCreated() {
        return created;
    }

    public JokeServiceModel setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
}
