package project.dailynail.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity{
    private String text;
    private UserEntity author;
    private Integer likes;
    private Integer dislikes;
    private ArticleEntity article;
    private LocalDateTime timePosted;

    public CommentEntity() {
    }

    @Column(columnDefinition = "TEXT")
    public String getText() {
        return text;
    }

    public CommentEntity setText(String text) {
        this.text = text;
        return this;
    }

    @ManyToOne
    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    @Column
    public Integer getLikes() {
        return likes;
    }

    public CommentEntity setLikes(Integer likes) {
        this.likes = likes;
        return this;
    }

    @Column
    public Integer getDislikes() {
        return dislikes;
    }

    public CommentEntity setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    @ManyToOne
    public ArticleEntity getArticle() {
        return article;
    }

    public CommentEntity setArticle(ArticleEntity article) {
        this.article = article;
        return this;
    }

    @Column(nullable = false)
    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    public CommentEntity setTimePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
        return this;
    }
}
